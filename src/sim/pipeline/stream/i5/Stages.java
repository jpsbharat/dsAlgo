package sim.pipeline.stream.i5;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public class Stages {

    public static <T, R> Stage<T, R> map(Stage<?, T> previousStage, Function<? super T, ? extends R> mapper) {
        return new Stage<T, R>(previousStage) {
            @Override
            public Sinks.Sink<T> process(Sinks.Sink<R> downstreamSink) {
                return new Sinks.Sink<T>() {
                    @Override
                    public void accept(T value) {
                        downstreamSink.accept(mapper.apply(value));
                    }
                };
            }
        };
    }

    public static <T> Stage<T, T> filter(Stage<?, T> previousStage, Predicate<? super T> predicate) {
        return new Stage<T, T>(previousStage) {
            @Override
            public Sinks.Sink<T> process(Sinks.Sink<T> downstreamSink) {
                return new Sinks.Sink<T>() {
                    @Override
                    public void accept(T value) {
                        if (predicate.test(value)) {
                            downstreamSink.accept(value);
                        }
                    }
                };
            }
        };
    }

    public static Stage<?, ?> findHead(Stage<?, ?> fromStage) {
        Stage<?, ?> head = fromStage;
        while (head.previousStage != null) {
            head = head.previousStage;
        }
        return head;
    }

    public static abstract class Stage<I, O> {
        private final Iterator<I> inputIterator;
        private final Stage<?, I> previousStage;

        // Constructor for the Head Stage
        public Stage(Iterator<I> inputIterator) {
            this.previousStage = null;
            this.inputIterator = inputIterator;
        }

        //Constructor for Intermediate Stages
        public Stage(Stage<?, I> previousStage) {
            this.previousStage = previousStage;
            this.inputIterator = null;
        }

        public Iterator<I> getInputIterator() {
            return inputIterator;
        }

        public Stage<?, I> getPreviousStage() {
            return previousStage;
        }

        public abstract Sinks.Sink<I> process(Sinks.Sink<O> downstreamSink);
    }
}
