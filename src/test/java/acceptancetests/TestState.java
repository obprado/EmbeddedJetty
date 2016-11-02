package acceptancetests;

import java.util.HashMap;
import java.util.Map;

public class TestState {

    private Map<String, Object> dataToBeAssertedOn = new HashMap<>();

    public TestState add(String key, Object value){
        dataToBeAssertedOn.put(key, value);
        return this;
    }

    public Object get(String key){
        return dataToBeAssertedOn.get(key);
    }
}
