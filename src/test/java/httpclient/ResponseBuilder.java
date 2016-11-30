package httpclient;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class ResponseBuilder {

    private Optional<Integer> statusCode = Optional.empty();
    private Optional<String> body = Optional.empty();
    private Optional<String> protocol = Optional.empty();

    private Multimap<String, String> headers = ArrayListMultimap.create();

    public ResponseBuilder statusCode(int statusCode) {
        this.statusCode = Optional.of(statusCode);
        return this;
    }

    public ResponseBuilder body(String body) {
        this.body = Optional.of(body);
        return this;
    }

    public ResponseBuilder protocol(String protocol) {
        this.protocol = Optional.of(protocol);
        return this;
    }

    public ResponseBuilder header(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public ResponseBuilder replaceHeader(String key, String value) {
        headers.removeAll(key);
        headers.put(key, value);
        return this;
    }

    public ResponseBuilder headers(Iterable<Header> headers) {
        for (Header header : headers) {
            header(header.key, header.value);
        }
        return this;
    }

    public Response build() {
        String body = this.body.orElseThrow(() -> new IllegalStateException("Body has not been specified"));
        int statusCode = this.statusCode.orElseThrow(() -> new IllegalStateException("Status code has not been specified"));
        String protocol = this.protocol.orElseThrow(() -> new IllegalStateException("Protocol has not been specified"));
        replaceHeader("Content-Length", String.valueOf(byteLength(body)));
        return new Response(body, statusCode, protocol, new Headers(headers));
    }

    private int byteLength(String body) {
        return body.getBytes(StandardCharsets.UTF_8).length;
    }
}
