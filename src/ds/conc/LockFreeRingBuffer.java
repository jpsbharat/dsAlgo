package ds.conc;

import java.util.concurrent.atomic.AtomicLong;

public class LockFreeRingBuffer<T> {
    private final Object[] buffer;
    private final int size;

    private final AtomicLong writeSeq = new AtomicLong(0);
    private final AtomicLong readSeq = new AtomicLong(0);

    public LockFreeRingBuffer(int capacity) {
        this.size = capacity;
        this.buffer = new Object[capacity];
    }

    public boolean offer(T data) {
        long currentWrite = writeSeq.get();
        long currentRead = readSeq.get();

        if (currentWrite - currentRead >= size) {
            // buffer full
            return false;
        }

        int index = (int) (currentWrite % size);
        buffer[index] = data;
        writeSeq.incrementAndGet();
        return true;
    }

    @SuppressWarnings("unchecked")
    public T poll() {
        long currentRead = readSeq.get();
        long currentWrite = writeSeq.get();

        if (currentRead >= currentWrite) {
            // buffer empty
            return null;
        }

        int index = (int) (currentRead % size);
        T data = (T) buffer[index];
        buffer[index] = null;
        readSeq.incrementAndGet();
        return data;
    }

    public static void main(String[] args) {
        LockFreeRingBuffer<Integer> rb = new LockFreeRingBuffer<>(4);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                while (!rb.offer(i)) {
                }
                System.out.println("Produced: " + i);
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                Integer value;
                while ((value = rb.poll()) == null) {
                }
                System.out.println("Consumed: " + value);
            }
        });

        producer.start();
        consumer.start();
    }
}
