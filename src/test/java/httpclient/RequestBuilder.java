package httpclient;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static httpclient.Header.CONTENT_LENGTH_KEY;

public class RequestBuilder {
    private Optional<String> url = Optional.empty();
    private Optional<String> method = Optional.empty();
    private Optional<String> body = Optional.empty();

    private Multimap<String, String> headers = ArrayListMultimap.create();
    private Map<String, String> queryParameters = new LinkedHashMap<>();

    public RequestBuilder method(String method) {
        this.method = Optional.of(method);
        return this;
    }

    public RequestBuilder body(String body) {
        this.body = Optional.of(body);
        return this;
    }

    public RequestBuilder url(String url) {
        this.url = Optional.of(url);
        return this;
    }

    public RequestBuilder header(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public void replaceHeader(String key, String newValue) {
        headers.removeAll(key);
        headers.put(key, newValue);
    }

    public RequestBuilder headers(Iterable<Header> headers) {
        for (Header header : headers) {
            header(header.key, header.value);
        }
        return this;
    }

    public Request build() {
        String method = this.method.orElseThrow(() -> new IllegalStateException("Method has not been specified"));
        String body = this.body.orElseThrow(() -> new IllegalStateException("Body has not been specified"));
        String url = this.url.orElseThrow(() -> new IllegalStateException("URL has not been specified"));
        replaceHeader(CONTENT_LENGTH_KEY, String.valueOf(contentLength()));
        return new Request(url, method, new Headers(headers), new QueryParameters(queryParameters), body);
    }

    private int contentLength() {
        return body.map(this::byteLength).orElse(0);
    }

    private int byteLength(String body) {
        return body.getBytes(StandardCharsets.UTF_8).length;
    }
}
