import java.util.*;
import java.util.logging.*;

public final class Motherboard {

    private static final Logger logger = Logger.getLogger(Motherboard.class.getName());

    private final Map<Integer, Device> devices; // a map of IDs to devices

    private Motherboard(Map<Integer, Device> devices) {
        this.devices = Map.copyOf(devices);
    }

    /* Static constructor */
    public static Motherboard with(Map<Integer, Device> devices) {
        Objects.requireNonNull(devices);
        devices.keySet().forEach(Objects::requireNonNull); // so that each device is associated with an ID
        if (devices.keySet().stream().anyMatch(t -> t == Message.BROADCAST_IDENTIFIER))
            throw new IllegalArgumentException("Devices cannot be mapped to the broadcast identifier");
        Motherboard m = new Motherboard(devices);
        devices.values().stream().filter(Objects::nonNull)
                                 .forEach(t -> t.setMotherboard(m)); // associates this motherboard with each device
        return m;
    }

    public Map<Integer, Device> getDevices() {
        return devices;
    }

    public Device getDevice(int recID) {
        return getDevices().get(recID);
    }

    /* Receives the message from the sending device and passes it on to the receiving device(s) */
    void send(Message message) {
        assert message.payload() != null;
        if (message.recID() == Message.BROADCAST_IDENTIFIER) {
            getDevices().values().stream()
                    .filter(t -> t.getApplication(message.portID()) != null)
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
}
