import java.util.*;
import java.util.logging.*;

public final class Device {

    private static final Logger logger = Logger.getLogger(Motherboard.class.getName());

    private Motherboard motherboard;
    private final Map<Integer, Application> portAssignments;

    private Device(Map<Integer, Application> assignments) {
        portAssignments = assignments;
    }

    public static Device with(Map<Integer, Application> apps) {
        Objects.requireNonNull(apps);
        apps.keySet().forEach(Objects::requireNonNull);
        Device d = new Device(Map.copyOf(apps));
        apps.values().forEach(t -> t.setDevice(d));
        return d;
    }

    public Motherboard getMotherboard() {
        return motherboard;
    }

    void setMotherboard(Motherboard m) {
        motherboard = m;
    }

    public Map<Integer, Application> getPortAssignments() {
        return portAssignments;
    }

    Application getApplication(int portID) {
        return getPortAssignments().get(portID);
    }

    void delegateSend(Message message) {
        getMotherboard().send(message);
    }

    void delegateReceive(Message message) {
        assert getMotherboard().getDevices().get(message.recID()) == this : "Message was not meant for this device";
        assert getPortAssignments().get(message.portID()) != null;

        Application app = getApplication(message.portID());

        if (app == null) {
            logger.log(Level.WARNING, "Message could not be sent: port ID does not match an existing application");
            return;
        }
        app.receive(message.payload());
    }
}
