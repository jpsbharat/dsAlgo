package sim.stream.i5;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;

public class Sinks {

    public interface Sink<T> extends Consumer<T> {
        default void begin(long size) {
        }

        default void end() {
        }
    }

    public static class TerminalSink<E> implements Sink<E> {
        private E result;
        private final BinaryOperator<E> accumulator;

        TerminalSink(E identity, BinaryOperator<E> accumulator) {
            this.result = identity;
            this.accumulator = accumulator;
        }

        @Override
        public void accept(E value) {
            this.result = accumulator.apply(this.result, value);
        }

        public E getResult() {
            return result;
        }
    }
}
