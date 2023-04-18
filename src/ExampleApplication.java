import java.util.List;

public class ExampleApplication extends SkeletalApplication {

    private List<String> messages;

    ExampleApplication(Motherboard motherboard) {
        super(motherboard);
    }

    @Override
    public void receive(String payload) {
        messages.add(payload);
    }

    public List<String> getMessages() {
        return List.copyOf(messages);
    }
}
