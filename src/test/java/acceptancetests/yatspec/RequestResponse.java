package acceptancetests.yatspec;

public class RequestResponse {
    private final String request;
    private final String response;

    RequestResponse(String request, String response) {
        this.request = request;
        this.response = response;
    }

    public String request() {
        return request;
    }

    public String response() {
        return response;
    }
}
