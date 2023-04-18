import java.util.*;

public interface Device {

    Device with(Motherboard m, Map<Integer, Application> apps);

    Motherboard getMotherboard();

    Map<Integer, Application> getPortAssignments();

    void delegate(Message message);
}
