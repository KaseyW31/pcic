import java.util.Map;

public class ExampleDevice extends SkeletalDevice{

    private ExampleDevice(Motherboard m, Map<Integer, Application> apps) {
        super(m, apps);
    }

    @Override
    public Device setUp(Motherboard m, Map<Integer, Application> apps) {
        // error checking
        return new ExampleDevice(m, apps);
    }
}
