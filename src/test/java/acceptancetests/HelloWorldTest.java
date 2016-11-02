package acceptancetests;

import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class HelloWorldTest extends AbstractAcceptanceTest {

    private final TestState testState = new TestState();
    private final Whens weMake = new Whens(testState);
    private final Thens the = new Thens(testState);


    @Test
    public void shouldReturnHelloWorld() throws Exception {
        when(weMake.aGetRequestTo("http://localhost:8080/hello"));
        then(the.statusCode(), is(200));
        then(the.body(), is("Hello from a servlet!!!!"));
    }

    @Test
    public void shouldFail() throws Exception {
        when(weMake.aGetRequestTo("http://localhost:8080/a/bad/url"));
        then(the.statusCode(), is(404));
    }

}