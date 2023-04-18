import java.util.*;

public abstract class SkeletalDevice implements Device {
    private final Motherboard motherboard;
    private final Map<Integer, Application> portAssignments;

    private SkeletalDevice(Motherboard motherboard, Map<Integer, Application> assignments) {
        this.motherboard = motherboard;
        portAssignments = assignments;
    }

    @Override
    public Map<Integer, Application> getPortAssignments() {
        return Map.copyOf(portAssignments);
    }

    @Override
    public void delegate(Message message) {
        getPortAssignments().get(message.portID()).receive(message);
    }
}
