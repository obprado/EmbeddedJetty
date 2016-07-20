import com.googlecode.yatspec.junit.SpecRunner;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpecRunner.class)
public class AcceptanceTest {

    private ExampleApp exampleApp = new ExampleApp();
    private HttpResponse response;

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
        whenWeMakeARequestTo("http://localhost:8080/hello");
        thenTheResponseCodeIs200AndTheBodyIs("Hello from a servlet!!!!");
    }

    @Test
    public void shouldFail() throws Exception {
        whenWeMakeARequestTo("http://localhost:8080/a/bad/url");
        thenItReturnsAStatusCodeOf(404);
    }

    private void thenItReturnsAStatusCodeOf(int expected) {
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(expected);
    }

    private void whenWeMakeARequestTo(String uri) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(uri);
        response = httpClient.execute(request);
    }

    private void thenTheResponseCodeIs200AndTheBodyIs(String expected) throws IOException {
        thenItReturnsAStatusCodeOf(200);
        assertThat(EntityUtils.toString(response.getEntity())).isEqualTo(expected);
    }
}