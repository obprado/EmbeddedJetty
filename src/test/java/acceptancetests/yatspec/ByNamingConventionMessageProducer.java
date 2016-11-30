package acceptancetests.yatspec;

import com.googlecode.yatspec.plugin.sequencediagram.SequenceDiagramMessage;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ByNamingConventionMessageProducer {
    private final static Pattern FULLY_QUALIFIED_MESSAGE_SEND_REGEXP =  Pattern.compile("(.*) from (.*) to (.*)");

    public List<SequenceDiagramMessage> messages(CapturedInputAndOutputs inputAndOutputs) {
        List<SequenceDiagramMessage> result = new ArrayList<>();
        Map<String, Object> types = inputAndOutputs.getTypes();
        Set<String> keys = types.keySet();
        for (String key : keys) {
            Matcher matcher = FULLY_QUALIFIED_MESSAGE_SEND_REGEXP.matcher(key);
            if (matcher.matches()) {
                String from = matcher.group(2).trim();
                String to = matcher.group(3).trim();
                String firstLineOfRequestOrResponse = types.get(key).toString().split("\n")[0];
                result.add(new SequenceDiagramMessage(escapeSpaces(from), escapeSpaces(to), firstLineOfRequestOrResponse, escapeSpaces(key)));
            }
        }
        return result;
    }

    private String escapeSpaces(String string) {
        return string.replaceAll(" ", "_");
    }
}