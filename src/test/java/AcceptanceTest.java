import com.googlecode.yatspec.junit.SpecRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(SpecRunner.class)
public class AcceptanceTest {

    @Test
    public void shouldShowASimpleOutput(){
        givenEverythingWillGoWell();
        whenWeDoSomething();
        thenTheResponseIsOk();
    }

    @Test
    public void shouldFail(){
        givenEverythingWillGoWell();
        whenSomethingFails();
    }

    private void whenSomethingFails() {  fail(); }
    private void givenEverythingWillGoWell() { }
    private void whenWeDoSomething() {}
    private void thenTheResponseIsOk() {}
}