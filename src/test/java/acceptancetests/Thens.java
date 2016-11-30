package acceptancetests;

import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import httpclient.Response;

public class Thens {

    private TestState testState;

    public Thens(TestState testState) {
        this.testState = testState;
    }

    public StateExtractor<Integer> statusCode() {
        return capturedInputAndOutputs ->
                ((Response)testState.get("response")).getStatusCode();
    }

    public StateExtractor<String> body() {
        return capturedInputAndOutputs -> ((Response)testState.get("response")).getBody();
    }
}
