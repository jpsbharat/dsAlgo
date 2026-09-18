package sim.stream.i4;

import java.util.Iterator;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

// 3. User Facing API that wraps the final leaf node of the AST
public class CustomPipeline<T> {
    // Each stream instance points to the current active trailing node in the pipeline
    private final Node<?, T> lastNode;

    // Constructor for the Head Stream
    private CustomPipeline(Iterator<T> sourceIterator) {
        this.lastNode = new Node<T, T>(sourceIterator) {
            @Override
            Sink<T> opWrapSink(Sink<T> downstreamSink) {
                return downstreamSink; // Head passes items cleanly
            }
        };
    }

    // Constructor for intermediate transformation streams
    private CustomPipeline(Node<?, T> lastNode) {
        this.lastNode = lastNode;
    }

    // Entry point factory method
    public static <T> CustomPipeline<T> of(Iterable<T> source) {
        return new CustomPipeline<>(source.iterator());
    }

    // --- INTERMEDIATE OPERATIONS ---

    public <R> CustomPipeline<R> map(Function<? super T, ? extends R> mapper) {
        // Create an explicit node bridging from current type T to new type R
        Node<T, R> mapNode = new Node<T, R>(this.lastNode) {
            @Override
            Sink<T> opWrapSink(Sink<R> downstreamSink) {
                return new Sink<T>() {
                    @Override
                    public void accept(T value) {
                        downstreamSink.accept(mapper.apply(value));
                    }
                };
            }
        };
        return new CustomPipeline<>(mapNode);
    }

    public CustomPipeline<T> filter(Predicate<? super T> predicate) {
        Node<T, T> filterNode = new Node<T, T>(this.lastNode) {
            @Override
            Sink<T> opWrapSink(Sink<T> downstreamSink) {
                return value -> {
                    if (predicate.test(value)) {
                        downstreamSink.accept(value);
                    }
                };
            }
        };
        return new CustomPipeline<>(filterNode);
    }

    // --- TERMINAL OPERATION (DRIVES PIPELINE) ---

    @SuppressWarnings({"rawtypes", "unchecked"})
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        // Build the final terminal state tracking sink
        TerminalSink<T> terminalSink = new TerminalSink<>(identity, accumulator);

        // Trace backward to find the absolute head node
        Node head = this.lastNode;
        while (head.previousStage != null) {
            head = head.previousStage;
        }

        // Build the nested listener chain moving backward from last node to first
        Sink chain = terminalSink;
        for (Node node = this.lastNode; node.previousStage != null; node = node.previousStage) {
            chain = node.opWrapSink(chain);
        }

        // Initialize lifecycle structural notifications
        chain.begin(-1);

        // Pull data out of head iterator and push down the Sink Pipeline
        Iterator iter = head.sourceIterator;
        while (iter.hasNext()) {
            chain.accept(iter.next());
        }

        chain.end();

        return terminalSink.getResult();
    }
}
