package acceptancetests;

import org.junit.Test;

import static acceptancetests.Thens.theBody;
import static acceptancetests.Thens.theStatusCode;
import static acceptancetests.Whens.weMakeAGetRequestTo;
import static org.hamcrest.core.Is.is;

public class HelloWorldTest extends AbstractAcceptanceTest {

    @Test
    public void shouldReturnHelloWorld() throws Exception {
        when(weMakeAGetRequestTo("http://localhost:8080/hello"));
        then(theStatusCode(), is(200));
        then(theBody(), is("Hello from a servlet!!!!"));
    }

    @Test
    public void shouldFail() throws Exception {
        when(weMakeAGetRequestTo("http://localhost:8080/a/bad/url"));
        then(theStatusCode(), is(404));
    }

}