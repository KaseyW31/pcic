import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class MotherboardTest {

    @Test
    public void testConstructorNominal() {
        Map<Integer, Device> deviceMap = new HashMap<>();
        Map<Integer, Application> appMap = Map.of (1, new ExampleApplication(),2, new ExampleApplication());
        deviceMap.put(100, Device.with(appMap));
        deviceMap.put(200, Device.with(appMap));
        Motherboard m = Motherboard.with(deviceMap);

        assertEquals(Set.of(100, 200), m.getDevices().keySet());
    }

    @Test
    public void testConstructorNullDevices() {
        assertThrows(NullPointerException.class, () -> Motherboard.with(null));
    }

    @Test
    public void testConstructorNullDeviceKeys() {
        Map<Integer, Device> deviceMap = new HashMap<>();
        Map<Integer, Application> appMap = Map.of (1, new ExampleApplication(),2, new ExampleApplication());
        deviceMap.put(null, Device.with(appMap));
        assertThrows(NullPointerException.class, () -> Motherboard.with(deviceMap));
    }

    @Test
    public void testConstructorBroadcastID() {
        Map<Integer, Device> deviceMap = new HashMap<>();
        Map<Integer, Application> appMap = Map.of (1, new ExampleApplication(),2, new ExampleApplication());
        deviceMap.put(Message.BROADCAST_IDENTIFIER, Device.with(appMap));
        assertThrows(IllegalArgumentException.class, () -> Motherboard.with(deviceMap));
    }

    @Test
    public void testConstructorDeviceSetMotherboard() {
        Device d1 = Device.with(Map.of(1, new ExampleApplication()));
        Map<Integer, Device> deviceMap = Map.of(1, d1);
        Motherboard m = Motherboard.with(deviceMap);

        assertEquals(m, d1.getMotherboard());
    }

    @Test
    public void testGetDeviceNominal() {
        Map<Integer, Device> deviceMap = new HashMap<>();
        Map<Integer, Application> appMap = Map.of (1, new ExampleApplication(),2, new ExampleApplication());
        Device d = Device.with(appMap);
        deviceMap.put(100, d);
        Motherboard m = Motherboard.with(deviceMap);
        assertEquals(d, m.getDevice(100));
    }

    @Test
    public void testGetDeviceNull() {
        Map<Integer, Device> deviceMap = new HashMap<>();
        Map<Integer, Application> appMap = Map.of (1, new ExampleApplication(),2, new ExampleApplication());
        Device d = Device.with(appMap);
        deviceMap.put(100, d);
        Motherboard m = Motherboard.with(deviceMap);
        assertNull(m.getDevice(0));
    }

    //

}
