import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class MotherboardTest {

    @Test
    public void testSetUpNominal() {
        Map<Integer, Device> deviceMap = new HashMap<>();
        Map<Integer, Application> appMap = new HashMap<>();
        appMap.put(1, new ExampleApplication());
        appMap.put(2, new ExampleApplication());
        deviceMap.put(100, Device.with(appMap));
        deviceMap.put(200, Device.with(appMap));
        Motherboard m = Motherboard.with(deviceMap);

    }

    // null device map

    // null keys for devices

}
