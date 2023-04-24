import java.util.*;
import java.util.logging.*;

/**
 * Represents a motherboard that Devices, with connected Applications, can connect to. A Motherboard contains
 * a mapping of IDs to devices, and connections to devices will be set up upon construction of a Motherboard.
 */
public final class Motherboard {

    private static final Logger logger = Logger.getLogger(Motherboard.class.getName());

    private final Map<Integer, Device> devices; // a map of IDs to devices

    private Motherboard(Map<Integer, Device> devices) {
        this.devices = Map.copyOf(devices);
    }

    /**
     * Static constructor for a Motherboard. The motherboard will be constructed with the provided mapping of IDs to
     * devices, and each of the devices to be connected will be associated with the constructed motherboard.
     * @param devices The non-null intended mapping of identifiers with devices.
     * @return A new Motherboard with devices connected as specified
     * @throws NullPointerException if the input map is null
     * @throws IllegalArgumentException if any device is mapped to the broadcast identifier
     */
    public static Motherboard with(Map<Integer, Device> devices) {
        Objects.requireNonNull(devices);
        // ensure each device is associated with an ID
        devices.keySet().forEach(Objects::requireNonNull);
        if (devices.keySet().stream().anyMatch(t -> t == Message.BROADCAST_IDENTIFIER))
            throw new IllegalArgumentException("Devices cannot be mapped to the broadcast identifier");
        Motherboard m = new Motherboard(devices);
        // associate each device in the input map with the constructed motherboard
        devices.values().stream().filter(Objects::nonNull)
                                 .forEach(t -> t.setMotherboard(m));
        return m;
    }

    /**
     * Returns the mapping of identifiers to connected devices
     * @return The map of IDs to devices
     */
    public Map<Integer, Device> getDevices() {
        return devices;
    }

    /**
     * Uses the map of IDs to devices contained in this Motherboard to return the device associated with a given ID
     * @param recID the ID that may be associated with a certain device
     * @return the Device associated with the given ID; null if no match found
     */
    public Device getDevice(int recID) {
        return getDevices().get(recID);
    }

    /**
     * Invoked by a sending device. Receives a message from the sending device and passes it on to the
     * receiving device(s). The receiving device will then delegate the message to the intended recipient application.
     * Logs a warning and terminates if no device is connected to the recipient ID on this Motherboard as specified in
     * the message.
     * @param message The message to be sent
     */
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
