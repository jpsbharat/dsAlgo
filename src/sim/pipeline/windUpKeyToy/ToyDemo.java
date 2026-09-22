package sim.pipeline.windUpKeyToy;

public class ToyDemo {
    public static void main(String[] args) {
        ToyPipeline.create()
                .key(1)
                .key(3)
                .key(1)
                .key(2).release();
    }
}
