import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.String.format;


public class StarWarsCharacterHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpGet lukeRequest = new HttpGet("http://swapi.co/api/people/1/");
        CloseableHttpResponse lukeResponse = HttpClientBuilder.create().build().execute(lukeRequest);

        String body = EntityUtils.toString(lukeResponse.getEntity());
        DocumentContext lukeJsonDocument = JsonPath.parse(body);
        String lukeName = lukeJsonDocument.read("$.name");

        String lukeSpeciesUrl = lukeJsonDocument.read("$.species[0]");
        String lukeHomeworldUrl = lukeJsonDocument.read("$.homeworld");

        HttpGet lukeSpeciesRequest = new HttpGet(lukeSpeciesUrl);
        CloseableHttpResponse lukeSpeciesResponse = HttpClientBuilder.create().build().execute(lukeSpeciesRequest);

        String lukeSpeciesBody = EntityUtils.toString(lukeSpeciesResponse.getEntity());
        String lukeSpecies = JsonPath.parse(lukeSpeciesBody).read("$.name");

        HttpGet lukeHomeworldRequest = new HttpGet(lukeHomeworldUrl);
        CloseableHttpResponse lukeHomeworldResponse = HttpClientBuilder.create().build().execute(lukeHomeworldRequest);
        String lukeHomeworldBody = EntityUtils.toString(lukeHomeworldResponse.getEntity());
        String lukeHomeworld = JsonPath.parse(lukeHomeworldBody).read("$.name");


        response.getWriter().print(format("{\"Description\": \"%s is a %s from %s\"}", lukeName, lukeSpecies, lukeHomeworld));
        response.setStatus(200);


    }
}
