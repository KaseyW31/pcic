import java.util.*;
import java.util.logging.*;

public final class Device {

    private static final Logger logger = Logger.getLogger(Device.class.getName());

    private Motherboard motherboard; // the motherboard that the device is plugged into
    private final Map<Integer, Application> portAssignments; // map of port IDs to applications

    private Device(Map<Integer, Application> assignments) {
        portAssignments = assignments;
    }

    /* Static constructor */
    public static Device with(Map<Integer, Application> apps) {
        Objects.requireNonNull(apps);
        apps.keySet().forEach(Objects::requireNonNull); // So that each application is associated with an ID
        Device d = new Device(Map.copyOf(apps));
        apps.values().stream().filter(Objects::nonNull)
                .forEach(t -> t.setDevice(d)); // associates each application with this device
        return d;
    }

    public Motherboard getMotherboard() {
        return motherboard;
    }

    /* Invoked by the motherboard when plugging the device in */
    void setMotherboard(Motherboard m) {
        motherboard = m;
    }

    public Map<Integer, Application> getPortAssignments() {
        return portAssignments;
    }

    /* Uses the map to return the application given an ID */
    Application getApplication(int portID) {
        return getPortAssignments().get(portID);
    }

    /* Receives the message from its application and passes it up to the motherboard */
    void delegateSend(Message message) {
        if (getMotherboard() == null) {
            logger.log(Level.WARNING, "Message could not be sent. Device is not connected to a motherboard");
            return;
        }
        getMotherboard().send(message);
    }

    /* Receives the message from the motherboard and passes it on to the receiving application */
    void delegateReceive(Message message) {
        Application app = getApplication(message.portID());
        if (app == null) {
            logger.log(Level.WARNING, "Message could not be sent: port ID does not match an existing application");
            return;
        }
        app.receive(message.payload());
    }
}
