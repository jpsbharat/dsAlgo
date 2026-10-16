package ds.dq;

public class DequeLinearArray<T> {
    private int size;
    private int head;
    private int tail;
    private T[] elems;

    public DequeLinearArray(int capacity) {
        this.size = 0;
        this.elems = (T[]) new Object[capacity * 2];
        this.head = capacity;
        this.tail = capacity;
    }

    public void offer(T t) {
        if (this.head == 0) {
            resize();
        }
        this.head--;
        this.elems[this.head] = t;
        this.size++;
    }

    public T poll() {
        if (this.size == 0) {
            throw new IllegalStateException("Deque is empty");
        }
        T t = this.elems[this.head];
        this.head++;
        this.size--;
        return t;
    }

    public T peekFirst() {
        if (this.size == 0) {
            throw new IllegalStateException("Deque is empty");
        }
        return this.elems[this.head];
    }

    public void push(T t) {
        if (this.head == 0) {
            resize();
        }
        this.elems[this.tail] = t;
        this.tail++;
        this.size++;

    }

    public T pop() {
        if (this.size == 0) {
            throw new IllegalStateException("Deque is empty");
        }
        this.tail--;
        T t = this.elems[this.tail];
        this.size--;
        return t;
    }

    public T peek() {
        if (this.size == 0) {
            throw new IllegalStateException("Deque is empty");
        }
        return this.elems[this.tail - 1];
    }

    private void resize() {
        int newCapacity = this.elems.length * 2;
        T[] newElems = (T[]) new Object[newCapacity];

        int newHead = (newCapacity - this.size) / 2;
        System.arraycopy(this.elems, this.head, newElems, newHead, this.size);

        this.elems = newElems;
        this.head = newHead;
        this.tail = newHead + this.size;
    }

    public int size() {
        return size;
    }
}
