package sim.stream.i4;

import java.util.Iterator;
import java.util.Objects;

// 2. The Absolute Base Node that supports transformation from E_IN to E_OUT
public abstract class Node<I, O> {
    protected final Node<?, I> previousStage;
    protected final Iterator<I> sourceIterator;

    // Head Node Constructor
    Node(Iterator<I> sourceIterator) {
        this.previousStage = null;
        this.sourceIterator = sourceIterator;
    }

    // Intermediate Node Constructor
    Node(Node<?, I> previousStage) {
        this.previousStage = Objects.requireNonNull(previousStage);
        this.sourceIterator = null;
    }

    // Wrap a downstream sink into an upstream sink
    abstract Sink<I> opWrapSink(Sink<O> downstreamSink);
}
