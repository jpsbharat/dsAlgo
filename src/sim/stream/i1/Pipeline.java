package sim.stream.i1;

public class Pipeline<I, O> {

    public interface  Stage<I, O>{
        O process(I input);
    }

    private final Stage<I, O> currentStage;

    public Pipeline(Stage<I, O> currentStage) {
        this.currentStage = currentStage;
    }

    // Chaining method: appends a new Stage by composing it with the current execution path
    public <K> Pipeline<I, K> addHandler(Stage<O, K> newStage) {
        return new Pipeline<>(input -> newStage.process(currentStage.process(input)));
    }

    // Triggers the actual execution of the composed pipeline
    public O execute(I input) {
        return currentStage.process(input);
    }
}
