import java.util.Map;
import java.util.Objects;

public class ExampleDevice extends Device{

    private ExampleDevice(Map<Integer, Application> apps) {
        super(apps);
    }

    public static Device with(Map<Integer, Application> apps) {
        Objects.requireNonNull(apps);
        apps.keySet().forEach(Objects::requireNonNull);
        return new ExampleDevice(apps);
    }
}
