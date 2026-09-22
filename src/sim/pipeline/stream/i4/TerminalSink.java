package sim.pipeline.stream.i4;

import java.util.function.BinaryOperator;

class TerminalSink<E> implements Sink<E> {
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
