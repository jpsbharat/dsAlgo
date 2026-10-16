package ds.dq;

public class DequeueCircularArray<T> {
    private int head;
    private int tail;
    private T[] elems;
    private int capacity;

    public DequeueCircularArray(int initialCapacity) {
        this.capacity = findNextPowerOfTwo(initialCapacity);
        this.elems = (T[]) new Object[capacity];
        this.head = 0;
        this.tail = 0;
    }

    public void offer(T t) {
        if (isFull()) {
            resize();
        }
        head = (head - 1) & (capacity - 1);
        elems[head] = t;
    }

    public T poll() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        T value = elems[head];
        head = (head + 1) & (capacity - 1);
        return value;
    }

    public T peekFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        return elems[head];
    }

    public void push(T t) {
        if (isFull()) {
            resize();
        }
        elems[tail] = t;
        tail = (tail + 1) & (capacity - 1);
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        tail = (tail - 1) & (capacity - 1);
        return elems[tail];
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        return elems[tail];
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public boolean isFull() {
        return ((tail + 1) & (capacity - 1)) == head;
    }

    public int size() {
        return (tail - head) & (capacity - 1);
    }

    private void resize() {
        int newCapacity = capacity << 1;
        T[] newArray = (T[]) new Object[newCapacity];

        int r = capacity - head;
        System.arraycopy(elems, head, newArray, 0, r);
        System.arraycopy(elems, 0, newArray, r, head);

        this.elems = newArray;
        this.head = 0;
        this.tail = capacity;
        this.capacity = newCapacity;
    }

    private int findNextPowerOfTwo(int num) {
        if (num <= 1) return 2;
        int n = num - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n + 1;
    }
}
