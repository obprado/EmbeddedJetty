package acceptancetests;

import org.junit.Test;

import static acceptancetests.Givens.*;
import static acceptancetests.Thens.*;
import static acceptancetests.Whens.*;
import static org.hamcrest.core.Is.is;

public class StarWarsTest extends AbstractAcceptanceTest {

    @Test
    public void shouldTalkAboutLukeSkywalkerByDefault() throws Exception {
        given(theStarWarsServiceKnowsAboutLuke());
        when(weMakeAGetRequestTo("http://localhost:8080/starWarsCharacter"));
        then(theStatusCode(), is(200));
        then(theBody(), is("{\"Description\": \"Luke Skywalker is a Human from Tatooine\"}"));
    }

}