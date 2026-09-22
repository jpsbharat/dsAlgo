package sim.pipeline.stream.i4;

import java.util.function.Consumer;

interface Sink<T> extends Consumer<T> {
    default void begin(long size) {
    }

    default void end() {
    }
}
