package acceptancetests;

import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

import static java.lang.String.format;

public class Whens {

    public static ActionUnderTest weMakeAGetRequestTo(String uri) {
        return (interestingGivens, capturedInputAndOutputs) -> whenWeMakeARequestTo(capturedInputAndOutputs, new HttpGet(uri));
    }

    private static CapturedInputAndOutputs whenWeMakeARequestTo(CapturedInputAndOutputs capturedInputAndOutputs, HttpGet request) throws IOException {
        capturedInputAndOutputs.add(format("Request from %s to %s", "a_user", "helloWorldApp"), request);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        capturedInputAndOutputs.add("response", response);
        capturedInputAndOutputs.add(format("Response from %s to %s", "helloWorldApp", "a_user"), response.getStatusLine().toString());
        return capturedInputAndOutputs;
    }
}
