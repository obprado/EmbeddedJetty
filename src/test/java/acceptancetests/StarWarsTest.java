package acceptancetests;

import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class StarWarsTest extends AbstractAcceptanceTest {

    private final TestState testState = new TestState();
    private final Givens theStarWarsService = new Givens(this);
    private final Whens weMake = new Whens(testState);
    private final Thens the = new Thens(testState);

    @Test
    public void shouldTalkAboutLukeSkywalkerByDefault() throws Exception {
        given(theStarWarsService.knowsAboutLuke());
        when(weMake.aGetRequestTo("http://localhost:8080/starWarsCharacter"));
        then(the.statusCode(), is(200));
        then(the.body(), is("{\"Description\": \"Luke Skywalker is a Human from Tatooine\"}"));
    }

}