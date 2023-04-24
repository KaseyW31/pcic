import java.util.logging.*;

/**
 * This class is an abstract representation of an Application that can be connected to a Device. To extend the class,
 * the user only needs to provide an implementation for the receive method, depending on their needs.
 */
public abstract class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    private Device device; // the device that the application is plugged into

    protected Application() {}

    /**
     * Returns the device that the application is connected to. In order to be connected to a device, a Device object
     * must be constructed with a map containing a mapping of a port ID to this application.
     * @return the device that this application is connected to; null if not connected
     */
    public Device getDevice() {
        return device;
    }

    /**
     * Invoked by the Device's static constructor when mapping port IDs to its connected applications.
     * @param d the Device that calls this method, which this application will be connected to
     */
    protected void setDevice(Device d) {
        device = d;
    }

    /**
     * Initiates the message sending process from this application to another. The message will be passed onto the
     * application's connected device (if present) to be delegated to the motherboard. Logs a warning and terminates
     * if this application is not connected to a device.
     * @param recID the ID of the recipient device, as mapped on the motherboard
     * @param portID the ID of the port application, as mapped on the receiving device
     * @param payload the message string to be sent
     */
    public void send(int recID, int portID, String payload) {
        if (getDevice() == null) {
            logger.log(Level.WARNING, "Message could not be sent. Application is not connected to a device");
            return;
        }
        if (payload == null)
            payload = "";
        Message message = new Message(recID, portID, payload);
        getDevice().delegateSend(message);
    }

    /**
     * Processes a message sent to this application.
     * @param payload The message that was sent
     */
    abstract void receive(String payload);
}
