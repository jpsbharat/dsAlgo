package ds.conc;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeQueue<E> {

    // Internal node structure
    private static class Node<E> {
        final E item;
        // Each node points to the next node atomically
        final AtomicReference<Node<E>> next;

        Node(E item) {
            this.item = item;
            this.next = new AtomicReference<>(null);
        }
    }

    // Head and Tail pointers are atomic references
    private final AtomicReference<Node<E>> head;
    private final AtomicReference<Node<E>> tail;

    public LockFreeQueue() {
        // Initialize with a dummy node to prevent head/tail collision on empty queue
        Node<E> dummy = new Node<>(null);
        head = new AtomicReference<>(dummy);
        tail = new AtomicReference<>(dummy);
    }

    /**
     * Enqueue (Insert) operation using CAS
     */
    public void enqueue(E item) {
        if (item == null) throw new NullPointerException();
        Node<E> newNode = new Node<>(item);

        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();

            // Verify tail hasn't changed out from under us
            if (curTail == tail.get()) {
                if (tailNext != null) {
                    // Lagging tail: Another thread added a node but didn't advance the tail.
                    // We help advance it before retrying.
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    // Try to link the new node to the end of the list
                    if (curTail.next.compareAndSet(null, newNode)) {
                        // Success! Now try to advance the tail pointer to our new node.
                        tail.compareAndSet(curTail, newNode);
                        return;
                    }
                }
            }
        }
    }

    /**
     * Dequeue (Remove) operation using CAS
     */
    public E dequeue() {
        while (true) {
            Node<E> curHead = head.get();
            Node<E> curTail = tail.get();
            Node<E> headNext = curHead.next.get();

            // Verify head hasn't changed
            if (curHead == head.get()) {
                if (curHead == curTail) {
                    if (headNext == null) {
                        // Queue is genuinely empty
                        return null;
                    }
                    // Tail is falling behind head. Help advance the tail pointer.
                    tail.compareAndSet(curTail, headNext);
                } else {
                    // Read the value before doing CAS (to avoid reading freed data)
                    E item = headNext.item;
                    // Try to advance the head pointer to the next node
                    if (head.compareAndSet(curHead, headNext)) {
                        return item; // Success
                    }
                }
            }
        }
    }
}