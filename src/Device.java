import java.util.*;

public abstract class Device {
    private Motherboard motherboard;
    private final Map<Integer, Application> portAssignments;

    protected Device(Map<Integer, Application> assignments) {
        portAssignments = assignments;
    }
    
    public Motherboard getMotherboard() {
        return motherboard;
    }

    protected void setMotherboard(Motherboard m) {
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
