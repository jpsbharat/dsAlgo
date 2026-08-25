package ds.conc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingBoundedQueue<E> {
    private final E[] items;
    private int takeIndex = 0;
    private int putIndex = 0;
    private int count = 0;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    @SuppressWarnings("unchecked")
    public BlockingBoundedQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException();
        this.items = (E[]) new Object[capacity];
    }

    /**
     * Inserts item, blocking if the queue is completely full.
     */
    public void put(E item) throws InterruptedException {
        if (item == null) throw new NullPointerException();
        lock.lockInterruptibly();
        try {
            while (count == items.length) {
                notFull.await(); // Sleep until a consumer takes an item
            }
            items[putIndex] = item;
            if (++putIndex == items.length) putIndex = 0; // Wrap around array
            count++;
            notEmpty.signal(); // Wake up any waiting consumers
        } finally {
            lock.unlock();
        }
    }

    /**
     * Extracts item, blocking if the queue is completely empty.
     */
    public E take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count == 0) {
                notEmpty.await(); // Sleep until a producer adds an item
            }
            E item = items[takeIndex];
            items[takeIndex] = null; // Clear reference for GC
            if (++takeIndex == items.length) takeIndex = 0; // Wrap around array
            count--;
            notFull.signal(); // Wake up any waiting producers
            return item;
        } finally {
            lock.unlock();
        }
    }
}