import java.util.*;

public abstract class SkeletalApplication implements Application {

    @Override
    public void send(Message message) {
        Collection<Device> recipients;
        if (message.recID() == Integer.MAX_VALUE)
            recipients = message.motherboard().getDevices().values();
        else
            recipients = Set.of(message.motherboard().getDevices().get(message.recID()));
        recipients.forEach(t -> t.delegate(message));
    }
}
