import java.util.List;

public class ExampleApplication extends SkeletalApplication {

    private List<String> messages;

    ExampleApplication(Motherboard motherboard) {
        super(motherboard);
    }

    public List<String> getMessages() {
        return List.copyOf(messages);
    }

    @Override
    public void receive(String payload) {
        assert payload != null;
        messages.add(payload);
    }
}
