package servlets;

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
        CloseableHttpResponse lukeResponse = getRequestTo("http://localhost:8888/api/people/1/");
        DocumentContext lukeJsonDocument = JsonPath.parse(EntityUtils.toString(lukeResponse.getEntity()));

        String lukeName = lukeJsonDocument.read("$.name");

        String lukeSpecies = extractName(getRequestTo(lukeJsonDocument.read("$.species[0]")));
        String lukeHomeworld = extractName(getRequestTo(lukeJsonDocument.read("$.homeworld")));

        response.getWriter().print(format("{\"Description\": \"%s is a %s from %s\"}", lukeName, lukeSpecies, lukeHomeworld));
        response.setStatus(200);
    }

    private String extractName(CloseableHttpResponse lukeSpeciesResponse) throws IOException {
        return JsonPath.parse(EntityUtils.toString(lukeSpeciesResponse.getEntity())).read("$.name");
    }

    private CloseableHttpResponse getRequestTo(String uri) throws IOException {
        HttpGet lukeRequest = new HttpGet(uri);
        return HttpClientBuilder.create().build().execute(lukeRequest);
    }
}
