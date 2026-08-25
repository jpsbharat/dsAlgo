package ds.conc;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class LockFreeBoundedQueue<E> {

    // Internal slot representation to track sequence tracking
    private static class Cell<T> {
        final AtomicLong sequence;
        T item;

        Cell(long seq) {
            this.sequence = new AtomicLong(seq);
        }
    }

    private final AtomicReferenceArray<Cell<E>> buffer;
    private final int capacity;
    private final int mask;

    private final AtomicLong enqueueIdx = new AtomicLong(0L);
    private final AtomicLong dequeueIdx = new AtomicLong(0L);

    public LockFreeBoundedQueue(int capacity) {
        // Capacity must be a power of 2 for fast bitwise modulo operations
        if ((capacity & (capacity - 1)) != 0) {
            throw new IllegalArgumentException("Capacity must be a power of 2");
        }
        this.capacity = capacity;
        this.mask = capacity - 1;
        this.buffer = new AtomicReferenceArray<>(capacity);
        for (int i = 0; i < capacity; i++) {
            buffer.set(i, new Cell<>(i));
        }
    }

    /**
     * Tries to add an item. Returns false immediately if full or contested.
     */
    public boolean offer(E item) {
        if (item == null) throw new NullPointerException();

        while (true) {
            long seq = enqueueIdx.get();
            int cellPos = (int) (seq & mask);
            Cell<E> cell = buffer.get(cellPos);
            long cellSeq = cell.sequence.get();
            long diff = cellSeq - seq;

            if (diff == 0) {
                // Slot is empty and ready for this exact sequence turn
                if (enqueueIdx.compareAndSet(seq, seq + 1)) {
                    cell.item = item;
                    cell.sequence.set(seq + 1); // Inform consumers data is ready
                    return true;
                }
            } else if (diff < 0) {
                // The queue is full
                return false;
            }
            // If diff > 0, another thread beat us; loop and retry
        }
    }

    /**
     * Tries to remove an item. Returns null immediately if empty.
     */
    public E poll() {
        while (true) {
            long seq = dequeueIdx.get();
            int cellPos = (int) (seq & mask);
            Cell<E> cell = buffer.get(cellPos);
            long cellSeq = cell.sequence.get();
            long diff = cellSeq - (seq + 1);

            if (diff == 0) {
                // Slot contains unconsumed data for this sequence turn
                if (dequeueIdx.compareAndSet(seq, seq + 1)) {
                    E item = cell.item;
                    cell.item = null; // Prevent memory leak
                    cell.sequence.set(seq + capacity); // Mark slot ready for next overwrite round
                    return item;
                }
            } else if (diff < 0) {
                // The queue is empty
                return null;
            }
            // Another thread grabbed the item first; loop and retry
        }
    }
}