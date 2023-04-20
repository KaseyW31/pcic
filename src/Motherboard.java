import java.util.*;
import java.util.logging.*;

public final class Motherboard {

    private static final Logger logger = Logger.getLogger(Motherboard.class.getName());


    private final Map<Integer, Device> devices;

    private Motherboard(Map<Integer, Device> devices) {
        this.devices = Map.copyOf(devices);
    }

    public static Motherboard with(Map<Integer, Device> devices) {
        Objects.requireNonNull(devices);
        devices.keySet().forEach(Objects::requireNonNull);
        Motherboard m = new Motherboard(devices);
        devices.values().stream().filter(Objects::nonNull).forEach(t -> t.setMotherboard(m));
        /* devices.values().stream().filter(Objects::nonNull)
                .forEach(t -> t.getPortAssignments().values().stream()
                        .filter(Objects::nonNull).forEach(s -> s.setMotherboard(m)));
         */

        return m;
    }

    Device getDevice(int recID) {
        return getDevices().get(recID);
    }

    void send(Message message) {
        if (message.recID() == Message.BROADCAST_IDENTIFIER) {
            getDevices().values().stream()
                    .filter(t -> t.getPortAssignments().get(message.portID()) != null)
                    .forEach(t -> t.delegateReceive(message));
        }
        else {
            Device device = getDevice(message.recID());
            if (device == null) {
                logger.log(Level.WARNING, "Message could not be sent: recipient ID does not match an existing device");
                return;
            }
            device.delegateReceive(message);
        }
    }

    Map<Integer, Device> getDevices() {
        return Map.copyOf(devices);
    }
}
