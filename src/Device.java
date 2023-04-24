import java.util.*;
import java.util.logging.*;

/**
 * This class represents a device, which can be attached to a Motherboard and contains a mapping of port
 * identifiers to Applications. Connections to applications will be set up upon construction of the device.
 * Connection to the motherboard will be set up upon construction of the motherboard.
 */
public final class Device {

    private static final Logger logger = Logger.getLogger(Device.class.getName());

    private Motherboard motherboard; // the motherboard that the device is plugged into
    private final Map<Integer, Application> portAssignments; // map of port IDs to applications

    private Device(Map<Integer, Application> assignments) {
        portAssignments = assignments;
    }

    /**
     * Static constructor for a Device. The device will be constructed with the provided mapping of port IDs to
     * applications, and each of the applications to be connected will be associated with the device.
     * @param apps The non-null intended mapping of port identifiers to applications.
     * @return A new Device with applications connected as specified
     * @throws NullPointerException if input map is null
     */
    public static Device with(Map<Integer, Application> apps) {
        Objects.requireNonNull(apps);
        // ensure each application is associated with an ID
        apps.keySet().forEach(Objects::requireNonNull);
        Device d = new Device(Map.copyOf(apps));
        // associate each application in the input map with the constructed device
        apps.values().stream().filter(Objects::nonNull).forEach(t -> t.setDevice(d));
        return d;
    }

    /**
     * Returns the mapping of port identifiers to connected applications
     * @return The map of port IDs to applications
     */
    public Map<Integer, Application> getPortAssignments() {
        return portAssignments;
    }

    /**
     * Returns the motherboard that the device is connected to. In order to be connected to a motherboard, a Motherboard
     * object must be constructed with a map containing a mapping of an ID to this device.
     * @return the motherboard that this device is plugged into; null if not connected
     */
    public Motherboard getMotherboard() {
        return motherboard;
    }

    /**
     * Invoked by the Motherboard's static constructor when mapping IDs to its connected devices.
     * @param m The Motherboard that calls this method, which this device will be connected to
     */
    void setMotherboard(Motherboard m) {
        assert m != null;
        motherboard = m;
    }

    /**
     * Uses the map of port IDs to maps contained in this Device to return the application associated with a given ID
     * @param portID the ID that may be associated with a certain application
     * @return the Application associated with the given ID; null if no match found
     */
    Application getApplication(int portID) {
        return getPortAssignments().get(portID);
    }

    /**
     * Receives an outgoing message from one of the connected applications and passes it up to the device's associated
     * Motherboard. The Motherboard will then send the message to the receiving device. Logs a warning and terminates
     * if this device is not connected to a motherboard.
     * @param message The message to be sent
     */
    void delegateSend(Message message) {
        assert message != null;
        if (getMotherboard() == null) {
            logger.log(Level.WARNING, "Message could not be sent. Device is not connected to a motherboard");
            return;
        }
        getMotherboard().send(message);
    }

    /**
     * Receives an incoming message from the motherboard and attempts to pass it on to the intended receiving
     * application. Logs a warning and terminates if there is no application mapped to the port identifier on this
     * device as specified in the message.
     * @param message The message to be sent
     */
    void delegateReceive(Message message) {
        assert message != null;
        Application recipientApp = getApplication(message.portID());
        if (recipientApp == null) {
            logger.log(Level.WARNING, "Message could not be sent: port ID does not match an existing application");
            return;
        }
        recipientApp.receive(message.payload());
    }
}
