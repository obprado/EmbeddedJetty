package acceptancetests;

import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

public class Whens {

    private TestState testState;

    public Whens(TestState testState) {
        this.testState = testState;
    }

    public ActionUnderTest aGetRequestTo(String uri) {
        return (interestingGivens, capturedInputAndOutputs) -> {
            HttpGet request = new HttpGet(uri);
            request.setHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
            return whenWeMakeARequestTo(capturedInputAndOutputs, request);
        };
    }

    private CapturedInputAndOutputs whenWeMakeARequestTo(CapturedInputAndOutputs capturedInputAndOutputs, HttpGet request) throws IOException {
        capturedInputAndOutputs.add(format("Request from %s to %s", "a_user", "helloWorldApp"), requestToString(request));
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        testState.add("response", response);
        capturedInputAndOutputs.add(format("Response from %s to %s", "helloWorldApp", "a_user"), response.getStatusLine().toString());
        return capturedInputAndOutputs;
    }

    private static String requestToString(HttpGet request) {
        return request.getMethod() + "     "  + request.getURI() + System.lineSeparator() + System.lineSeparator() +
                extractHeaders(request);
    }

    private static String extractHeaders(HttpGet request) {
        return Arrays.asList(request.getAllHeaders()).stream().map(Header::toString).collect(joining());
    }
}
