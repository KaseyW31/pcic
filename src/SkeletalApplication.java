import java.util.*;

public abstract class SkeletalApplication implements Application {

    private final Motherboard motherboard;

    protected SkeletalApplication(Motherboard motherboard) {
        this.motherboard = motherboard;
    }

    @Override
    public Motherboard getMotherboard() {
        return motherboard;
    }

    @Override
    public void send(boolean isBroadcast, int recID, int portID, String payload) {
        Message message = Message.from(isBroadcast, recID, portID, payload);
        Collection<Device> recipients;
        if (isBroadcast)
            recipients = Collections.unmodifiableCollection(getMotherboard().getDevices().values());
        else
            recipients = Set.of(getMotherboard().getDevices().get(message.recID()));
        recipients.forEach(t -> t.delegate(message));
    }
}
