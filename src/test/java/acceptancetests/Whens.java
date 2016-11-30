package acceptancetests;

import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;

import java.io.IOException;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

public class Whens {
    private static final String CALLER = "star wars user";
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
        capturedInputAndOutputs.add(format("Request from %s to %s", CALLER, AbstractAcceptanceTest.APPLICATION_NAME), httpclient.Request.toNiceRequestForYatspec(request));
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        httpclient.Response domainResponse = httpclient.Response.fromApacheResponse(response);
        testState.add("response", domainResponse);
        capturedInputAndOutputs.add(format("Response from %s to %s", AbstractAcceptanceTest.APPLICATION_NAME, CALLER), domainResponse);
        return capturedInputAndOutputs;
    }

}
