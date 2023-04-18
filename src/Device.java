import java.util.*;

public final class Device {
    private Motherboard motherboard;
    private final Map<Integer, Application> portAssignments;

    Device(Map<Integer, Application> assignments) {
        portAssignments = assignments;
    }

    public static Device with(Map<Integer, Application> apps) {
        Objects.requireNonNull(apps);
        apps.keySet().forEach(Objects::requireNonNull);
        return new Device(Map.copyOf(apps));
    }

    public Motherboard getMotherboard() {
        return motherboard;
    }

    void setMotherboard(Motherboard m) {
        motherboard = m;
    }

    public Map<Integer, Application> getPortAssignments() {
        return Map.copyOf(portAssignments);
    }

    void delegate(Message message) {
        assert getMotherboard().getDevices().get(message.recID()) == this : "Message was not meant for this device";
        assert getPortAssignments().get(message.portID()) != null;
        getPortAssignments().get(message.portID()).receive(message.payload());
    }
}
