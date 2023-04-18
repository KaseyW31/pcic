import java.util.*;

public interface Device {

    Map<Integer, Application> getPortAssignments();

    void delegate(Message message);
}
