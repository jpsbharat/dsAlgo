package sim.pipeline.windUpKeyToy;

/**
 * ToyPipeline class implements the Toy interface and represents a pipeline of wind-up stages.
 *
 * @param <T> the type of energy used to wind up the toy, which must extend Integer
 */
public class ToyPipeline<T extends Integer> implements Toy<T> {

    private WindUpStage<T> prevStage;

    // Constructor to initialize the ToyPipeline with a specific energy value
    private ToyPipeline() {
        this.prevStage = new WindUpStage<T>() {
            @Override
            public EnergySink<T> process(EnergySink<T> downstreamSink) {

                return new EnergySink<T>() {
                    @Override
                    public void burn(T energy) {
                        // Process the energy and pass it to the downstream sink
                        System.out.println("Processing with initial energy: " + energy);
                        downstreamSink.burn(energy);
                    }
                };
            }
        };
    }

    private ToyPipeline(WindUpStage<T> prevStage) {
        this.prevStage = prevStage;
    }

    /**
     * Creates a new ToyPipeline instance.
     *
     * @return a new ToyPipeline instance
     */
    public static Toy<Integer> create() {
        return new ToyPipeline<>();
    }

    /**
     * Adds a new wind-up stage to the pipeline with the specified energy.
     *
     * @param energySupplied the energy to be supplied by the new wind-up stage to the toy.
     * @return a new ToyPipeline instance with the added wind-up stage
     */
    @Override
    public ToyPipeline<T> key(T energySupplied) {
        WindUpStage<T> keyStage = new WindUpStage<T>(prevStage, energySupplied) {
            @Override
            public EnergySink<T> process(EnergySink<T> downstreamSink) {
                return new EnergySink<>() {
                    @Override
                    public void burn(T energyAvailable) {
                        // Process the energy and pass it to the downstream sink
                        System.out.println("Key stage processing -> energy available: " + energyAvailable + ", energy required: " + energySupplied);
                        downstreamSink.burn((T) Integer.valueOf(energyAvailable.intValue() - energySupplied.intValue()));
                    }
                };
            }
        };
        return new ToyPipeline<>(keyStage);
    }

    /**
     * Releases the toy, calculating the total stored energy from the supplied energy to all the key stages.
     * This total energy is then processed through the pipeline.
     */
    @Override
    public void release() {
        T energyStored = (T) Integer.valueOf(0);
        WindUpStage<T> curr = prevStage;
        while (curr != null && curr.prevStage != null) {
            energyStored = (T) Integer.valueOf(energyStored.intValue() + curr.energy.intValue());
            curr = curr.prevStage;
        }

        System.out.println("Total stored energy: " + energyStored);
        WindUpStage<T> head = curr;
        head.energy = energyStored;

        EnergySink<T> sink = new EnergySink<>() {
            @Override
            public void burn(T energy) {
                System.out.println("Final energy after release completed: " + energy);
            }
        };
        for (WindUpStage<T> stage = prevStage; stage != null && stage.prevStage != null; stage = stage.prevStage) {
            sink = stage.process(sink);
        }

        sink.burn(head.energy);
    }

}
