package acceptancetests;

import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class Thens {

    public static StateExtractor<Integer> theStatusCode() {
        return capturedInputAndOutputs ->
                capturedInputAndOutputs.getType("response", HttpResponse.class).getStatusLine().getStatusCode();
    }

    public static StateExtractor<String> theBody() {
        return capturedInputAndOutputs -> EntityUtils.toString(capturedInputAndOutputs.getType("response", HttpResponse.class).getEntity());
    }
}
