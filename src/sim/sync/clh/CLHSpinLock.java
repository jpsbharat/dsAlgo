package sim.sync.clh;

import java.util.concurrent.atomic.AtomicReference;

/**
 * A basic Exclusive Lock implementation using a CLH Queue.
 * Mimics how AQS sequences threads under the hood via node references.
 */
public class CLHSpinLock {

    /// Represents a thread in the wait queue
    private static class Node {
        /// true = thread is waiting for the lock or holds it
        /// false = thread has released the lock
        private volatile boolean isLocked = true;
    }

    /// The Tail pointer always points to the last node that joined the queue.
    /// Updated atomically using CAS.
    private final AtomicReference<Node> tail;

    /// ThreadLocal to hold each thread's personal node and predecessor node references
    private final ThreadLocal<Node> myNode;
    private final ThreadLocal<Node> myPredecessor;

    public CLHSpinLock() {
        /// Initialize the queue with a dummy node.
        /// At birth, the queue is un-owned (isLocked = false).
        Node dummyNode = new Node();
        dummyNode.isLocked = false;

        this.tail = new AtomicReference<>(dummyNode);

        /// Initialize ThreadLocal node factories
        this.myNode = ThreadLocal.withInitial(Node::new);
        this.myPredecessor = ThreadLocal.withInitial(() -> null);
    }

    /**
     * Acquires the lock. Enqueues the thread and spins until the predecessor releases it.
     */
    public void lock() {
        Node node = myNode.get();
        /// Ensure the node is marked as locked/waiting before joining the queue
        node.isLocked = true;

        /// CAS operation:
        ///    Atomically place our node at the tail of the queue.
        ///    The old tail node becomes our predecessor.
        Node pred = tail.getAndSet(node);
        myPredecessor.set(pred);

        /// --- THE CLH UNDER-THE-HOOD SPIN ---
        /// Instead of watching a global lock state, this thread exclusively
        /// monitors its predecessor's 'isLocked' flag. This minimizes CPU cache traffic.
        while (pred.isLocked) {
            /// Spin-waiting. (In true AQS, this would call LockSupport.park() to save CPU)
            Thread.onSpinWait();
        }

        /// Once pred.isLocked becomes false, we have officially acquired the lock!
    }

    /**
     * Releases the lock and signals the next waiting thread.
     */
    public void unlock() {
        Node node = myNode.get();

        /// Changing our status to false instantly unblocks the thread spinning right behind us.
        node.isLocked = false;

        /// Optimization/Recycling step:
        /// We reuse our predecessor's node object for our thread's future lock requests.
        /// This keeps allocation to an absolute minimum.
        myNode.set(myPredecessor.get());
    }
}