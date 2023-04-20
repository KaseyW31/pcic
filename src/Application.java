import java.util.logging.*;

public abstract class Application {

    private static final Logger logger = Logger.getLogger(Motherboard.class.getName());

    private Device device;

    protected Application() {}

    public Device getDevice() {
        return device;
    }

    protected void setDevice(Device d) {
        device = d;
    }

    public void send(int recID, int portID, String payload) {
        if (getDevice() == null)
            logger.log(Level.WARNING, "Message could not be sent. Application is not connected to a device");
        Message message = new Message(recID, portID, payload);
        getDevice().delegateSend(message);
    }

    abstract void receive(String payload);
}
