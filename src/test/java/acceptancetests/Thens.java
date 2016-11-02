package acceptancetests;

import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class Thens {

    private TestState testState;

    public Thens(TestState testState) {
        this.testState = testState;
    }

    public StateExtractor<Integer> statusCode() {
        return capturedInputAndOutputs ->
                ((HttpResponse)testState.get("response")).getStatusLine().getStatusCode();
    }

    public StateExtractor<String> body() {
        return capturedInputAndOutputs -> EntityUtils.toString(((HttpResponse)testState.get("response")).getEntity());
    }
}
