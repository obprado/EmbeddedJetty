package httpclient;

class QueryParameter extends ValueType {
    private final String key;
    private final String value;

    QueryParameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
