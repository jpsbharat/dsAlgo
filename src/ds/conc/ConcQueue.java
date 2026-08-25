package ds.conc;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ConcQueue<T> {
    private final AtomicInteger size;
    private final AtomicReference<Node<T>> tail;
    private final AtomicReference<Node<T>> head;

    public ConcQueue() {
        size = new AtomicInteger();
        head = new AtomicReference<>(null);
        tail = new AtomicReference<>(null);
        size.set(0);
    }

    private static class Node<T> {
        private volatile T value;
        private volatile Node<T> next;
        private volatile Node<T> previous;

        public Node(T value) {
            this.value = value;
            this.next = null;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<T> previous) {
            this.previous = previous;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    public T get() {
        if (head.get() == null) {
            throw new NoSuchElementException();
        }

        Node<T> currentHead;
        Node<T> nextNode;
        do {
            currentHead = head.get();
            nextNode = currentHead.getNext();
        } while (!head.compareAndSet(currentHead, nextNode));

        size.decrementAndGet();
        return currentHead.getValue();
    }

    public void add(T element) {
        if (element == null) {
            throw new NullPointerException();
        }

        Node<T> node = new Node<>(element);
        Node<T> currentTail;
        do {
            currentTail = tail.get();
            node.setPrevious(currentTail);
        } while (!tail.compareAndSet(currentTail, node));

        if (node.previous != null) {
            node.previous.next = node;
        }

        head.compareAndSet(null, node); // for inserting the first element
        size.incrementAndGet();
    }
}
