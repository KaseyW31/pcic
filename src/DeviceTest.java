import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class DeviceTest {

    @Test
    public void testConstructorNominal() {
        Application app1 = new ExampleApplication();
        Application app2 = new ExampleApplication();
        Map<Integer, Application> map = Map.of(1, app1, 2, app2);
        Device device = Device.with(map);

        assertEquals(map, device.getPortAssignments());
    }

    @Test
    public void testConstructorNullApps() {
        assertThrows(NullPointerException.class, () -> Device.with(null));
    }

    @Test
    public void testConstructorNullDeviceKeys() {
        Map<Integer, Application> appMap = new HashMap<>();
        appMap.put(1, new ExampleApplication());
        appMap.put(null, new ExampleApplication());
        assertThrows(NullPointerException.class, () -> Device.with(appMap));
    }

    @Test
    public void testConstructorAppSetDevice() {
        Application a1 = new ExampleApplication();
        Map<Integer, Application> map = Map.of(1, a1);
        Device d = Device.with(map);

        assertEquals(d, a1.getDevice());
    }

}
