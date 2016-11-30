package httpclient;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.joining;

class QueryParameters extends ValueType implements Iterable<QueryParameter> {

    private final Map<String, String> queryParameters;

    QueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    static QueryParameters empty() {
        return new QueryParameters(new HashMap<>());
    }

    @Override
    public Iterator<QueryParameter> iterator() {
        return queryParameters.entrySet().stream()
                .map(entry -> new QueryParameter(entry.getKey(), entry.getValue()))
                .iterator();
    }

    @Override
    public String toString() {
        if (queryParameters.isEmpty()) {
            return "";
        }
        Stream<QueryParameter> stream = StreamSupport.stream(spliterator(), false);
        return stream.map(QueryParameter::toString).collect(joining("&", "?", ""));
    }
}
