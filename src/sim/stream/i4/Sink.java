package sim.stream.i4;

import java.util.function.*;

interface Sink<T> extends Consumer<T> {
    default void begin(long size) {}
    default void end() {}
}
