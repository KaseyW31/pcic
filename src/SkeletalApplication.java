import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SkeletalApplication implements Application {

    private Motherboard motherboard;

    private static final Logger logger = Logger.getLogger(SkeletalApplication.class.getName());

    protected SkeletalApplication() {
    }

    @Override
    public Motherboard getMotherboard() {
        return motherboard;
    }

    @Override
    public void setMotherboard(Motherboard m) {
        motherboard = m;
    }

    @Override
    public void send(boolean isBroadcast, int recID, int portID, int payload) {
        String binaryPayload = Integer.toBinaryString(payload);
        Message message = Message.from(isBroadcast, recID, portID, binaryPayload);

        if (isBroadcast)
            getMotherboard().getDevices().values().stream()
                    .filter(t -> t.getPortAssignments().get(portID) != null)
                    .forEach(t -> t.delegate(message));
        else {
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

}
