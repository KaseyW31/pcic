import java.util.ArrayList;
import java.util.List;

/**
 * An example implementation of the Application class. It processes messages by adding them to a list catalog of
 * messages.
 */
public class ExampleApplication extends Application {

    private final List<String> messages = new ArrayList<>();

    /**
     * Returns an unmodifiable copy of the message history.
     * @return The list of messages received by this application
     */
    public List<String> getMessages() {
        return List.copyOf(messages);
    }

    /**
     * Processes incoming messages by adding the payload to the messages list contained in the class. The payload
     * must not be null because any null payloads should have been converted to empty strings upon sending the message.
     * @param payload The payload of the incoming message
     */
    @Override
    void receive(String payload) {
        assert payload != null;
        messages.add(payload);
    }

    /**
     * Returns the last message that this application received, null if no message has been received yet.
     * @return the last received message
     */
    public String getLastMessage() {
        List<String> list = getMessages();
        if (list.size() > 0)
            return list.get(list.size() - 1);
        else
            return null;
    }
}
