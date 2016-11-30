package acceptancetests.yatspec;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import static java.lang.String.format;

public class RequestAndResponsesFormatter {

    private Multiset<String> requestCounts = HashMultiset.create();

    public synchronized RequestResponse requestResponse(String from, String to) {
        int count = requestCount(from, to);
        String request = requestFromXToY(from, to, count);
        String response = responseFromYToX(to, from, count);
        return new RequestResponse(request, response);
    }

    public String uncountedRequestFromXToY(String from, String to) {
        return format("Request from %s to %s", from, to);
    }

    public String uncountedResponseFromYToX(String from, String to) {
        return format("Response from %s to %s", from, to);
    }

    private int requestCount(String from, String to) {
        String requestType = from + "-" + to;
        requestCounts.add(requestType);
        return requestCounts.count(requestType);
    }

    private String requestFromXToY(String from, String to, int requestCount) {
        if (requestCount == 1) {
            return uncountedRequestFromXToY(from, to);
        } else {
            return format("Request %d from %s to %s", requestCount, from, to);
        }
    }

    private String responseFromYToX(String from, String to, int requestCount) {
        if (requestCount == 1) {
            return uncountedResponseFromYToX(from, to);
        } else {
            return format("Response %d from %s to %s", requestCount, from, to);
        }
    }
}
