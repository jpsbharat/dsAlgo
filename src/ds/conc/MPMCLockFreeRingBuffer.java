package ds.conc;

import java.util.concurrent.atomic.AtomicLong;

public class MPMCLockFreeRingBuffer<E> {
    private static class Cell<E> {
        final AtomicLong sequence = new AtomicLong(0);
        volatile E value;
    }

    private final int bufferSize;
    private final int indexMask;
    private final Cell<E>[] buffer;

    private final AtomicLong enqueueSequence = new AtomicLong(0);
    private final AtomicLong dequeueSequence = new AtomicLong(0);

    @SuppressWarnings("unchecked")
    public MPMCLockFreeRingBuffer(int capacity) {
        if (Integer.bitCount(capacity) != 1) {
            throw new IllegalArgumentException("Capacity must be a power of 2");
        }
        this.bufferSize = capacity;
        this.indexMask = capacity - 1;
        this.buffer = (Cell<E>[]) new Cell[capacity];
        for (int i = 0; i < capacity; i++) {
            Cell<E> cell = new Cell<>();
            cell.sequence.set(i);
            buffer[i] = cell;
        }
    }

    public boolean offer(E item) {
        if (item == null) throw new NullPointerException();

        long currentEnqueueSeq;
        Cell<E> cell;
        while (true) {
            currentEnqueueSeq = enqueueSequence.get();
            cell = buffer[(int) (currentEnqueueSeq & indexMask)];
            long seq = cell.sequence.get();
            long dif = seq - currentEnqueueSeq;

            if (dif == 0) {
                if (enqueueSequence.compareAndSet(currentEnqueueSeq, currentEnqueueSeq + 1)) {
                    break;
                }
            } else if (dif < 0) {
                return false; // full
            } else {
                Thread.yield();
                continue;
            }
        }

        cell.value = item;
        cell.sequence.set(currentEnqueueSeq + 1);
        return true;
    }

    public E poll() {
        long currentDequeueSeq;
        Cell<E> cell;
        while (true) {
            currentDequeueSeq = dequeueSequence.get();
            cell = buffer[(int) (currentDequeueSeq & indexMask)];
            long seq = cell.sequence.get();
            long dif = seq - (currentDequeueSeq + 1);

            if (dif == 0) {
                if (dequeueSequence.compareAndSet(currentDequeueSeq, currentDequeueSeq + 1)) {
                    break;
                }
            } else if (dif < 0) {
                return null; // empty
            } else {
                Thread.yield();
                continue;
            }
        }

        E value = cell.value;
        cell.value = null;
        cell.sequence.set(currentDequeueSeq + bufferSize);
        return value;
    }

    public int size() {
        return (int) (enqueueSequence.get() - dequeueSequence.get());
    }

    public static void main(String[] args) {
        MPMCLockFreeRingBuffer<Integer> rb = new MPMCLockFreeRingBuffer<>(2);
        int P = 3;
        int C = 5;
        Thread producer = new Thread(() -> {
            for (int i = 1; i < P; i++) {
                while (!rb.offer(i)) {
                }
                System.out.println("Produced: " + i);
            }
        }, "Producer");

        Thread producer1 = new Thread(() -> {
            for (int i = 1; i < P; i++) {
                while (!rb.offer(i)) {
                }
                System.out.println("Produced1: " + i);
            }
        }, "Producer1");

        Thread producer2 = new Thread(() -> {
            for (int i = 1; i < P; i++) {
                while (!rb.offer(i)) {
                }
                System.out.println("Produced2: " + i);
            }
        }, "Producer2");

        Thread consumer = new Thread(() -> {
            for (int i = 1; i < C; i++) {
                Integer value;
                while ((value = rb.poll()) == null) {
                }
                System.out.println("Consumed: " + value);
            }
        }, "Consumer");

        Thread consumer1 = new Thread(() -> {
            for (int i = 1; i < C; i++) {
                Integer value;
                while ((value = rb.poll()) == null) {
                }
                System.out.println("Consumed1: " + value);
            }
        }, "Consumer1");

        consumer1.start();
        producer.start();
        producer1.start();
        consumer.start();
        producer2.start();
    }
}
