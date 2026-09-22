package sim.pipeline.windUpKeyToy;

public abstract class WindUpStage<T extends Integer> {
    T energy;
    WindUpStage<T> prevStage;
    WindUpStage<T> nextStage;

    public WindUpStage() {
        this.prevStage = null;
        this.nextStage = null;
    }

    public WindUpStage(WindUpStage<T> prevStage, T energy) {
        this.prevStage = prevStage;
        this.prevStage.nextStage = this;
        this.energy = energy;
    }

    public abstract EnergySink<T> process(EnergySink<T> downstreamSink);
}
