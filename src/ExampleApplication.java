import java.util.List;

public class ExampleApplication extends Application {

    private List<String> messages;

    public List<String> getMessages() {
        return List.copyOf(messages);
    }

    @Override
    public void receive(String payload) {
        assert payload != null;
        messages.add(payload);
    }
}
