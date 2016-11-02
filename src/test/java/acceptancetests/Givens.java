package acceptancetests;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

public class Givens {
    private AbstractAcceptanceTest abstractAcceptanceTest;

    public Givens(AbstractAcceptanceTest abstractAcceptanceTest) {
        this.abstractAcceptanceTest = abstractAcceptanceTest;
    }

    private void registerListener() {
        abstractAcceptanceTest.listenToWiremock(this::recordTraffic);
    }

    private void recordTraffic(Request request, Response response) {
        abstractAcceptanceTest.addToCapturedInputsAndOutputs("request from helloWorldApp to swapi", request);
        abstractAcceptanceTest.addToCapturedInputsAndOutputs("response from swapi to helloWorldApp", response);
    }

    public GivensBuilder knowsAboutLuke() {
        registerListener();
        return interestingGivens -> {
            WireMock wiremock = new WireMock(8888);
            wiremock.register(
                    get(urlMatching("/api/people/1/"))
                    .willReturn(WireMock.aResponse().withStatus(200).withBody("{\n" +
                            "    \"name\": \"Luke Skywalker\", \n" +
                            "    \"height\": \"172\", \n" +
                            "    \"mass\": \"77\", \n" +
                            "    \"hair_color\": \"blond\", \n" +
                            "    \"skin_color\": \"fair\", \n" +
                            "    \"eye_color\": \"blue\", \n" +
                            "    \"birth_year\": \"19BBY\", \n" +
                            "    \"gender\": \"male\", \n" +
                            "    \"homeworld\": \"http://localhost:8888/api/planets/1/\", \n" +
                            "    \"films\": [\n" +
                            "        \"http://localhost:8888/api/films/6/\", \n" +
                            "        \"http://localhost:8888/api/films/3/\", \n" +
                            "        \"http://localhost:8888/api/films/2/\", \n" +
                            "        \"http://localhost:8888/api/films/1/\", \n" +
                            "        \"http://localhost:8888/api/films/7/\"\n" +
                            "    ], \n" +
                            "    \"species\": [\n" +
                            "        \"http://localhost:8888/api/species/1/\"\n" +
                            "    ], \n" +
                            "    \"vehicles\": [\n" +
                            "        \"http://localhost:8888/api/vehicles/14/\", \n" +
                            "        \"http://localhost:8888/api/vehicles/30/\"\n" +
                            "    ], \n" +
                            "    \"starships\": [\n" +
                            "        \"http://localhost:8888/api/starships/12/\", \n" +
                            "        \"http://localhost:8888/api/starships/22/\"\n" +
                            "    ], \n" +
                            "    \"created\": \"2014-12-09T13:50:51.644000Z\", \n" +
                            "    \"edited\": \"2014-12-20T21:17:56.891000Z\", \n" +
                            "    \"url\": \"http://localhost:8888/api/people/1/\"\n" +
                            "}")));
            wiremock.register(
                    get(urlMatching("/api/planets/1/"))
                            .willReturn(WireMock.aResponse().withStatus(200).withBody("{\n" +
                                    "    \"name\": \"Tatooine\", \n" +
                                    "    \"rotation_period\": \"23\", \n" +
                                    "    \"orbital_period\": \"304\", \n" +
                                    "    \"diameter\": \"10465\", \n" +
                                    "    \"climate\": \"arid\", \n" +
                                    "    \"gravity\": \"1 standard\", \n" +
                                    "    \"terrain\": \"desert\", \n" +
                                    "    \"surface_water\": \"1\", \n" +
                                    "    \"population\": \"200000\", \n" +
                                    "    \"residents\": [\n" +
                                    "        \"http://localhost:8888/api/people/1/\", \n" +
                                    "        \"http://localhost:8888/api/people/2/\", \n" +
                                    "        \"http://localhost:8888/api/people/4/\", \n" +
                                    "        \"http://localhost:8888/api/people/6/\", \n" +
                                    "        \"http://localhost:8888/api/people/7/\", \n" +
                                    "        \"http://localhost:8888/api/people/8/\", \n" +
                                    "        \"http://localhost:8888/api/people/9/\", \n" +
                                    "        \"http://localhost:8888/api/people/11/\", \n" +
                                    "        \"http://localhost:8888/api/people/43/\", \n" +
                                    "        \"http://localhost:8888/api/people/62/\"\n" +
                                    "    ], \n" +
                                    "    \"films\": [\n" +
                                    "        \"http://localhost:8888/api/films/5/\", \n" +
                                    "        \"http://localhost:8888/api/films/4/\", \n" +
                                    "        \"http://localhost:8888/api/films/6/\", \n" +
                                    "        \"http://localhost:8888/api/films/3/\", \n" +
                                    "        \"http://localhost:8888/api/films/1/\"\n" +
                                    "    ], \n" +
                                    "    \"created\": \"2014-12-09T13:50:49.641000Z\", \n" +
                                    "    \"edited\": \"2014-12-21T20:48:04.175778Z\", \n" +
                                    "    \"url\": \"http://localhost:8888/api/planets/1/\"\n" +
                                    "}")));
            wiremock.register(
                    get(urlMatching("/api/species/1/"))
                            .willReturn(WireMock.aResponse().withStatus(200).withBody("{\n" +
                                    "    \"name\": \"Human\", \n" +
                                    "    \"classification\": \"mammal\", \n" +
                                    "    \"designation\": \"sentient\", \n" +
                                    "    \"average_height\": \"180\", \n" +
                                    "    \"skin_colors\": \"caucasian, black, asian, hispanic\", \n" +
                                    "    \"hair_colors\": \"blonde, brown, black, red\", \n" +
                                    "    \"eye_colors\": \"brown, blue, green, hazel, grey, amber\", \n" +
                                    "    \"average_lifespan\": \"120\", \n" +
                                    "    \"homeworld\": \"http://localhost:8888/api/planets/9/\", \n" +
                                    "    \"language\": \"Galactic Basic\", \n" +
                                    "    \"people\": [\n" +
                                    "        \"http://localhost:8888/api/people/1/\", \n" +
                                    "        \"http://localhost:8888/api/people/4/\", \n" +
                                    "        \"http://localhost:8888/api/people/5/\", \n" +
                                    "        \"http://localhost:8888/api/people/6/\", \n" +
                                    "        \"http://localhost:8888/api/people/7/\", \n" +
                                    "        \"http://localhost:8888/api/people/9/\", \n" +
                                    "        \"http://localhost:8888/api/people/10/\", \n" +
                                    "        \"http://localhost:8888/api/people/11/\", \n" +
                                    "        \"http://localhost:8888/api/people/12/\", \n" +
                                    "        \"http://localhost:8888/api/people/14/\", \n" +
                                    "        \"http://localhost:8888/api/people/18/\", \n" +
                                    "        \"http://localhost:8888/api/people/19/\", \n" +
                                    "        \"http://localhost:8888/api/people/21/\", \n" +
                                    "        \"http://localhost:8888/api/people/22/\", \n" +
                                    "        \"http://localhost:8888/api/people/25/\", \n" +
                                    "        \"http://localhost:8888/api/people/26/\", \n" +
                                    "        \"http://localhost:8888/api/people/28/\", \n" +
                                    "        \"http://localhost:8888/api/people/29/\", \n" +
                                    "        \"http://localhost:8888/api/people/32/\", \n" +
                                    "        \"http://localhost:8888/api/people/34/\", \n" +
                                    "        \"http://localhost:8888/api/people/43/\", \n" +
                                    "        \"http://localhost:8888/api/people/51/\", \n" +
                                    "        \"http://localhost:8888/api/people/60/\", \n" +
                                    "        \"http://localhost:8888/api/people/61/\", \n" +
                                    "        \"http://localhost:8888/api/people/62/\", \n" +
                                    "        \"http://localhost:8888/api/people/66/\", \n" +
                                    "        \"http://localhost:8888/api/people/67/\", \n" +
                                    "        \"http://localhost:8888/api/people/68/\", \n" +
                                    "        \"http://localhost:8888/api/people/69/\", \n" +
                                    "        \"http://localhost:8888/api/people/74/\", \n" +
                                    "        \"http://localhost:8888/api/people/81/\", \n" +
                                    "        \"http://localhost:8888/api/people/84/\", \n" +
                                    "        \"http://localhost:8888/api/people/85/\", \n" +
                                    "        \"http://localhost:8888/api/people/86/\", \n" +
                                    "        \"http://localhost:8888/api/people/35/\"\n" +
                                    "    ], \n" +
                                    "    \"films\": [\n" +
                                    "        \"http://localhost:8888/api/films/7/\", \n" +
                                    "        \"http://localhost:8888/api/films/5/\", \n" +
                                    "        \"http://localhost:8888/api/films/4/\", \n" +
                                    "        \"http://localhost:8888/api/films/6/\", \n" +
                                    "        \"http://localhost:8888/api/films/3/\", \n" +
                                    "        \"http://localhost:8888/api/films/2/\", \n" +
                                    "        \"http://localhost:8888/api/films/1/\"\n" +
                                    "    ], \n" +
                                    "    \"created\": \"2014-12-10T13:52:11.567000Z\", \n" +
                                    "    \"edited\": \"2015-04-17T06:59:55.850671Z\", \n" +
                                    "    \"url\": \"http://localhost:8888/api/species/1/\"\n" +
                                    "}")));
            return interestingGivens;
        };
    }
}
