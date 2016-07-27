import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpecRunner.class)
public class AcceptanceTest extends TestState {

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
    }

    @Test
    public void shouldReturnHelloWorld() throws Exception {
        when(weMakeAGetRequestTo("http://localhost:8080/hello"));
        thenTheResponseCodeIs200AndTheBodyIs("Hello from a servlet!!!!");
    }

    @Test
    public void shouldFail() throws Exception {
        when(weMakeAGetRequestTo("http://localhost:8080/a/bad/url"));
        thenItReturnsAStatusCodeOf(404);
    }

    private ActionUnderTest weMakeAGetRequestTo(String uri) {
        return (interestingGivens, capturedInputAndOutputs) -> whenWeMakeARequestTo(capturedInputAndOutputs, new HttpGet(uri));
    }

    private void thenItReturnsAStatusCodeOf(int expected) {
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(expected);
    }

    private CapturedInputAndOutputs whenWeMakeARequestTo(CapturedInputAndOutputs capturedInputAndOutputs, HttpGet request) throws IOException {
        capturedInputAndOutputs.add("Request", request);
        response = HttpClientBuilder.create().build().execute(request);
        responseBody = EntityUtils.toString(response.getEntity());
        capturedInputAndOutputs.add("Response", responseBody);
        return capturedInputAndOutputs;
    }

    private void thenTheResponseCodeIs200AndTheBodyIs(String expected) throws IOException {
        thenItReturnsAStatusCodeOf(200);
        assertThat(responseBody).isEqualTo(expected);
    }
}