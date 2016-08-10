import com.googlecode.yatspec.junit.SpecResultListener;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.junit.WithCustomResultListeners;
import com.googlecode.yatspec.plugin.sequencediagram.ByNamingConventionMessageProducer;
import com.googlecode.yatspec.plugin.sequencediagram.SequenceDiagramGenerator;
import com.googlecode.yatspec.plugin.sequencediagram.SvgWrapper;
import com.googlecode.yatspec.rendering.html.DontHighlightRenderer;
import com.googlecode.yatspec.rendering.html.HtmlResultRenderer;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpecRunner.class)
public class StarWarsTest extends TestState implements WithCustomResultListeners {

    private ExampleApp exampleApp = new ExampleApp();
    private HttpResponse response;
    private String responseBody;

    @Before
    public void setUp() throws Exception {
        exampleApp.run();
    }

    @After
    public void tearDown() throws Exception {
        exampleApp.stop();
        capturedInputAndOutputs.add("Sequence Diagram", generateSequenceDiagram());
    }

    private SvgWrapper generateSequenceDiagram() {
        return new SequenceDiagramGenerator().generateSequenceDiagram(new ByNamingConventionMessageProducer().messages(capturedInputAndOutputs));
    }

    @Override
    public Iterable<SpecResultListener> getResultListeners() throws Exception {
        return singletonList(new HtmlResultRenderer()
                .withCustomHeaderContent(SequenceDiagramGenerator.getHeaderContentForModalWindows())
                .withCustomRenderer(SvgWrapper.class, new DontHighlightRenderer<>()));
    }

    @Test
    public void shouldTalkAboutLukeSkywalkerByDefault() throws Exception {
        when(weMakeAGetRequestTo("http://localhost:8080/starWarsCharacter"));
        thenTheResponseCodeIs200AndTheBodyIs("{\"Description\": \"Luke Skywalker is a Human from Tatooine\"}");
    }

    private ActionUnderTest weMakeAGetRequestTo(String uri) {
        return (interestingGivens, capturedInputAndOutputs) -> whenWeMakeARequestTo(capturedInputAndOutputs, new HttpGet(uri));
    }

    private void thenItReturnsAStatusCodeOf(int expected) {
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(expected);
    }

    private CapturedInputAndOutputs whenWeMakeARequestTo(CapturedInputAndOutputs capturedInputAndOutputs, HttpGet request) throws IOException {
        capturedInputAndOutputs.add(format("Request from %s to %s", "a_user", "helloWorldApp"), request);
        response = HttpClientBuilder.create().build().execute(request);
        responseBody = EntityUtils.toString(response.getEntity());
        capturedInputAndOutputs.add(format("Response from %s to %s", "helloWorldApp", "a_user"), response.getStatusLine().toString());
        return capturedInputAndOutputs;
    }

    private void thenTheResponseCodeIs200AndTheBodyIs(String expected) throws IOException {
        thenItReturnsAStatusCodeOf(200);
        assertThat(responseBody).isEqualTo(expected);
    }
}