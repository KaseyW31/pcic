import java.util.*;

public final class Motherboard {
    private final Map<Integer, Device> devices;

    private Motherboard(Map<Integer, Device> devices) {
        this.devices = Map.copyOf(devices);
    }

    public static Motherboard with(Map<Integer, Device> devices) {
        Objects.requireNonNull(devices);
        devices.keySet().forEach(Objects::requireNonNull);
        Motherboard m = new Motherboard(devices);
        devices.values().stream().filter(Objects::nonNull).forEach(t -> t.setMotherboard(m));
        /* devices.values().stream().filter(Objects::nonNull)
                .forEach(t -> t.getPortAssignments().values().stream()
                        .filter(Objects::nonNull).forEach(s -> s.setMotherboard(m)));
         */

        return m;
    }

    Map<Integer, Device> getDevices() {
        return Map.copyOf(devices);
    }
}
