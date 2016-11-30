package acceptancetests.yatspec;

import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import httpclient.Header;
import httpclient.RequestBuilder;
import httpclient.ResponseBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class YatspecFormatters {
    public static String toYatspecString(Request wireMockRequest) {
        return new RequestBuilder()
                .body(adaptBody(wireMockRequest.getBodyAsString()))
                .headers(adaptHeaders(wireMockRequest.getHeaders()))
                .method(wireMockRequest.getMethod().getName())
                .url(wireMockRequest.getUrl())
                .build()
                .toString();
    }

    public static String toYatspecString(Response wiremockResponse) {
        return new ResponseBuilder()
                .protocol("HTTP/1.1")
                .body(adaptBody(wiremockResponse.getBodyAsString()))
                .headers(fixWiremockListenerProblem(adaptHeaders(wiremockResponse.getHeaders())))
                .statusCode(wiremockResponse.getStatus())
                .build()
                .toString();
    }

    private static Iterable<Header> fixWiremockListenerProblem(List<Header> headers) {
        return headers.stream().map(YatspecFormatters::appendGzipToEtag).collect(Collectors.toList());
    }

    private static Header appendGzipToEtag(Header header) {
        return header.key.toLowerCase().equals("etag") ? new Header(header.key, header.value + "--gzip")  : header;
    }

    private static List<Header> adaptHeaders(HttpHeaders headers) {
        return headers.all().stream()
                .map(httpHeader -> new Header(httpHeader.key(), httpHeader.values()))
                .collect(Collectors.toList());
    }

    private static String adaptBody(String wiremockBody) {
        if (wiremockBody == null) {
            return "";
        } else {
            return wiremockBody;
        }
    }
}
