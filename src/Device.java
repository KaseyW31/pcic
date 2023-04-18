import java.util.*;

public abstract class Device {
    private final Motherboard motherboard;
    private final Map<Integer, Application> portAssignments;

    protected Device(Motherboard motherboard, Map<Integer, Application> assignments) {
        this.motherboard = motherboard;
        portAssignments = assignments;
    }

    public abstract Device setUp(Motherboard m, Map<Integer, Application> apps);

    public Motherboard getMotherboard() {
        return motherboard;
    }

    public Map<Integer, Application> getPortAssignments() {
        return Map.copyOf(portAssignments);
    }

    void delegate(Message message) {
        assert getPortAssignments().get(message.portID()) != null;
        getPortAssignments().get(message.portID()).receive(message.payload());
    }
}
