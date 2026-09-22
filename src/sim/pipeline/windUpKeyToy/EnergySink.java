package sim.pipeline.windUpKeyToy;

public class EnergySink<T extends Integer> {
    public void burn(T energy) {
        System.out.println("Energy.burn: " + energy);
    }
}
