package sim.stream.i5;

import java.util.Iterator;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class Pipeline<T> {
    private final Stages.Stage<?, T> previousStage;

    private Pipeline(Iterator<T> inputIterator) {
        this.previousStage = new Stages.Stage<T, T>(inputIterator) {
            @Override
            public Sinks.Sink<T> process(Sinks.Sink<T> downstreamSink) {
                return downstreamSink; // Head stage just passes through the sink
            }
        };
    }

    private Pipeline(Stages.Stage<?, T> previousStage) {
        this.previousStage = previousStage;
    }

    public static <T> Pipeline<T> from(Iterable<T> input) {
        return new Pipeline<>(input.iterator());
    }

    public <R> Pipeline<R> map(Function<? super T, ? extends R> mapper) {
        return new Pipeline<>(Stages.map(previousStage, mapper));
    }

    public Pipeline<T> filter(Predicate<? super T> predicate) {
        return new Pipeline<>(Stages.filter(previousStage, predicate));
    }


    public <R> R reduce(R identity, BinaryOperator<R> accumulator) {
        Sinks.TerminalSink<R> terminalSink = new Sinks.TerminalSink<>(identity, accumulator);
        Stages.Stage<?, ?> head = Stages.findHead(previousStage);
        Sinks.Sink sink = terminalSink;
        for (Stages.Stage<?, ?> stage = this.previousStage; stage != null && stage.getPreviousStage() != null; stage = stage.getPreviousStage()) {
            sink = stage.process(sink);

        }

        sink.begin(-1);

        // Pull data out of head iterator and push down the Sink Pipeline
        Iterator iter = head.getInputIterator();
        while (iter.hasNext()) {
            sink.accept(iter.next());
        }

        sink.end();

        return terminalSink.getResult();
    }
}
