package sim.stream.i3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CustomStream<T> {
    // Holds the initial source collection
    private final List<T> source;
    // Tracks intermediate transformations to apply sequentially
    private final List<Function<Object, Object>> stages;

    // Private constructor used internally for immutability during chaining
    private CustomStream(List<T> source, List<Function<Object, Object>> stages) {
        this.source = source;
        this.stages = stages;
    }

    // Static factory method to start the stream pipeline
    public static <T> CustomStream<T> of(List<T> source) {
        return new CustomStream<>(source, new ArrayList<>());
    }

    /**
     * Intermediate Operation: Map
     * Stores the transformation function without executing it.
     */
    @SuppressWarnings("unchecked")
    public <R> CustomStream<R> map(Function<T, R> mapper) {
        List<Function<Object, Object>> updatedStages = new ArrayList<>(this.stages);
        // Add the mapper to our processing pipeline
        updatedStages.add((Function<Object, Object>) mapper);
        return new CustomStream<>((List<R>) this.source, updatedStages);
    }

    /**
     * Terminal Operation: Reduce
     * Triggers pipeline execution, passes data through all map stages, and aggregates it.
     */
    public T reduce(T identity, BiFunction<T, T, T> accumulator) {
        T result = identity;

        for (T item : source) {
            Object currentProcessedValue = item;

            // Lazily evaluate all registered intermediate map stages for the current item
            for (Function<Object, Object> stage : stages) {
                currentProcessedValue = stage.apply(currentProcessedValue);
            }

            // Accumulate the fully transformed value
            @SuppressWarnings("unchecked")
            T transformedItem = (T) currentProcessedValue;
            result = accumulator.apply(result, transformedItem);
        }

        return result;
    }
}
