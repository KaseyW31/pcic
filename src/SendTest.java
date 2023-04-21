import java.util.logging.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

public class SendTest {

    private LogHandler handler;

    private ExampleApplication a1;
    private ExampleApplication a2;
    private ExampleApplication a3;
    private ExampleApplication a4;
    private ExampleApplication a5;
    private ExampleApplication a6;
    private Device d1;
    private Device d2;
    private Device d3;
    private Motherboard m;

    /* A setup of a motherboard with a few devices, each with a few applications */
    public void normalSetup() {
        a1 = new ExampleApplication();
        a2 = new ExampleApplication();
        a3 = new ExampleApplication();
        a4 = new ExampleApplication();
        a5 = new ExampleApplication();
        a6 = new ExampleApplication();
        d1 = Device.with(Map.of(1, a1, 2, a2, 3, a3));
        d2 = Device.with(Map.of(1, a4, 2, a5));
        d3 = Device.with(Map.of(1, a6));
        m = Motherboard.with(Map.of(1, d1, 2, d2,3,d3));
    }

    /* Used to test log output */
    public void setupLogger(String className) {
        Logger logger = Logger.getLogger(className);
        handler = new LogHandler();
        handler.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
    }

    @Test
    public void testNominal() {
        normalSetup();
        a4.send(1, 3, "Ahoy");
        assertEquals("Ahoy", a3.getLastMessage());
    }

    @Test
    public void testDisconnectedApplication() {
        setupLogger("Application");
        ExampleApplication a1 = new ExampleApplication();
        a1.send(1, 3, "bonjour");
        assertTrue(handler.checkMessage().contains("Application is not connected"));
    }

    @Test
    public void testBroadcast() {
        normalSetup();
        a4.send(Message.BROADCAST_IDENTIFIER, 1, "hola");
        assertEquals(a1.getLastMessage(), "hola");
        assertEquals(a4.getLastMessage(), "hola");
        assertEquals(a6.getLastMessage(), "hola");
        assertEquals(0, a3.getMessages().size());
    }

    @Test
    public void testNullPayload() {
        normalSetup();
        a1.send(2, 1, null);
        assertEquals("", a4.getLastMessage());
    }

    @Test
    public void testUnconnectedDevice() {
        normalSetup();
        setupLogger("Device");
        Application a7 = new ExampleApplication();
        Device d4 = Device.with(Map.of(1, a7));
        a7.send(1, 1, "ciao");
        assertNull(a1.getLastMessage());
        assertTrue(handler.checkMessage().contains("Device is not connected"));
    }

    @Test
    public void testReceivingDeviceNotFound() {
        normalSetup();
        setupLogger("Motherboard");
        a1.send(4, 1, "konnichiwa");
        assertTrue(handler.checkMessage().contains("recipient ID does not match"));
    }

    @Test
    public void testReceivingAppNotFound() {
        normalSetup();
        setupLogger("Device");
        a1.send(2, 36, "merhaba");
        assertTrue(handler.checkMessage().contains("port ID does not match"));
    }

    /* A motherboard with 1000 devices, each with 1000 applications. Each application sends a message back to itself.
    Assert that each application has received one message at the end.
     */
    @Test
    public void stressTest() {
        NavigableMap<Integer, Application> apps = new TreeMap<>();
        for (int i = 0; i < 1000000; i++) {
            apps.put(i, new ExampleApplication());
        }
        Map<Integer, Device> deviceMap = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            deviceMap.put(i, Device.with(apps.subMap(1000*i, 1000*(i+1))));
        }
        Motherboard m = Motherboard.with(deviceMap);
        for (int i = 0; i < apps.size(); i++) {
            Application app = apps.get(i);
            app.send(i / 1000, i, "hi");
        }
        for (Application app : apps.values()) {
            assertEquals(1, ((ExampleApplication) app).getMessages().size());
        }

    }

    /* Helper class to test log output */
    static class LogHandler extends Handler
    {
        String lastMessage = "";

        public String checkMessage() {
            return lastMessage;
        }

        public void publish(LogRecord record) {
            lastMessage = record.getMessage();
        }

        public void close(){}
        public void flush(){}
    }
}
