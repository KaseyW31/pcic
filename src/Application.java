import java.util.logging.*;

public abstract class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    private Device device; // the device that the application is plugged into

    protected Application() {}

    public Device getDevice() {
        return device;
    }

    /* Invoked by the device when plugging the application in */
    protected void setDevice(Device d) {
        device = d;
    }

    /* Starting point of sending a message to another application */
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

    abstract void receive(String payload);
}
