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
        deviceMap.put(100, ExampleDevice.with(appMap));

    }

}
