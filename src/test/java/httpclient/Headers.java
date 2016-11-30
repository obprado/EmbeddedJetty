package httpclient;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

class Headers extends ValueType implements Iterable<Header> {

    private final Multimap<String, String> headers;

    Headers(Multimap<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Iterator<Header> iterator() {
        return headers.entries().stream()
                .map(entry -> new Header(entry.getKey(), entry.getValue()))
                .iterator();
    }

    public static Headers fromApacheHeaders(org.apache.http.Header[] headers){
        Multimap<String, String> accumulator = ArrayListMultimap.create();
        for (org.apache.http.Header header : headers){
            accumulator.put(header.getName(), header.getValue());
        }
        return new Headers(accumulator);
    }

    @Override
    public String toString() {
        Stream<Header> stream = StreamSupport.stream(spliterator(), false);
        return stream.map(Header::toString).collect(joining(lineSeparator()));
    }
}
