import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Application {

    private Motherboard motherboard;

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    protected Application() {
    }

    public Motherboard getMotherboard() {
        return motherboard;
    }

    protected void setMotherboard(Motherboard m) {
        motherboard = m;
    }

    public void send(boolean isBroadcast, int recID, int portID, int payload) {
        String binaryPayload = Integer.toBinaryString(payload);
        Message message;

        if (isBroadcast) {
            message = new Message(Integer.MAX_VALUE, portID, binaryPayload);
            getMotherboard().getDevices().values().stream()
                    .filter(t -> t.getPortAssignments().get(portID) != null)
                    .forEach(t -> t.delegate(message));
        }
        else {
            message = new Message(recID, portID, binaryPayload);
            Device device = getMotherboard().getDevices().get(recID);
            if (device == null) {
                logger.log(Level.WARNING, "Message could not be sent: recipient ID does not match an existing device");
                return;
            }
            else if (device.getPortAssignments().get(portID) == null) {
                logger.log(Level.WARNING, "Message could not be sent: port ID does not match an existing application");
                return;
            }
            device.delegate(message);
        }
    }

    abstract void receive(String payload);
}
