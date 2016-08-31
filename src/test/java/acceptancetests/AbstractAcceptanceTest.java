package acceptancetests;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.AnythingPattern;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import com.googlecode.yatspec.junit.SpecResultListener;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.junit.WithCustomResultListeners;
import com.googlecode.yatspec.plugin.sequencediagram.ByNamingConventionMessageProducer;
import com.googlecode.yatspec.plugin.sequencediagram.SequenceDiagramGenerator;
import com.googlecode.yatspec.plugin.sequencediagram.SvgWrapper;
import com.googlecode.yatspec.rendering.html.DontHighlightRenderer;
import com.googlecode.yatspec.rendering.html.HtmlResultRenderer;
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import server.ExampleApp;

import static java.util.Collections.singletonList;

@RunWith(SpecRunner.class)
public abstract class AbstractAcceptanceTest extends TestState implements WithCustomResultListeners {

    private static final int WIREMOCK_PORT = 8888;
    private ExampleApp exampleApp = new ExampleApp();
    private WireMockServer wireMockServer;

    @Before
    public void setUp() throws Exception {
        exampleApp.run();
        startWiremock();
        primeDefaultWiremockMessage();
    }

    @After
    public void tearDown() throws Exception {
        exampleApp.stop();
        capturedInputAndOutputs.add("Sequence Diagram", generateSequenceDiagram());
        wireMockServer.stop();
    }

    private void startWiremock() {
        wireMockServer = new WireMockServer(WIREMOCK_PORT);
        wireMockServer.start();
    }

    private void primeDefaultWiremockMessage() {
        new WireMock(WIREMOCK_PORT).register(WireMock.any(new UrlPattern(new AnythingPattern(), true)).willReturn(new ResponseDefinitionBuilder().withBody("These are not the droids you're looking for").withStatus(666)));
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
}
