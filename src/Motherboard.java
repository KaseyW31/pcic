import java.util.*;

public final class Motherboard {
    private final Map<Integer, Device> devices;

    private Motherboard(Map<Integer, Device> devices) {
        this.devices = Map.copyOf(devices);
    }

    static Motherboard setUp(Map<Integer, Device> devices) {
        // error checking?
        return new Motherboard(devices);
    }

    Map<Integer, Device> getDevices() {
        return Map.copyOf(devices);
    }
}
