import java.util.ArrayList;
import java.util.List;

public class ExampleApplication extends Application {

    private final List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return List.copyOf(messages);
    }

    @Override
    void receive(String payload) {
        assert payload != null;
        messages.add(payload);
    }

    public String getLastMessage() {
        List<String> list = getMessages();
        if (list.size() > 0)
            return list.get(list.size() - 1);
        else
            return null;
    }
}
