package sim.sync.clh;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * A CLH-based synchronizer that uses Thread Parking instead of spin-waiting,
 * closely mirroring Java's underlying AQS engine.
 */
public class CLHParkableLock {

    // Represents a node in the queue
    private static class Node {
        // Keeps track of the thread waiting on this node
        private volatile Thread thread;

        // STATUS FLAGS (Mimics AQS waitStatus):
        // true  = Thread is still waiting or holding the lock
        // false = Thread has released the lock and is finished
        private volatile boolean isLocked = true;
    }

    // The entry tail pointer of our queue
    private final AtomicReference<Node> tail;

    // ThreadLocal storage to maintain each thread's node references
    private final ThreadLocal<Node> myNode;
    private final ThreadLocal<Node> myPredecessor;

    public CLHParkableLock() {
        // Initialize the queue with an inactive dummy node
        Node dummyNode = new Node();
        dummyNode.isLocked = false;

        this.tail = new AtomicReference<>(dummyNode);
        this.myNode = ThreadLocal.withInitial(Node::new);
        this.myPredecessor = ThreadLocal.withInitial(() -> null);
    }

    /**
     * Acquires the lock. Enqueues the thread and parks it if the lock is busy.
     */
    public void lock() {
        Node node = myNode.get();
        node.isLocked = true;
        // Bind the current thread to this node so the predecessor can unpark it later
        node.thread = Thread.currentThread();

        // Atomically append to the tail of the queue
        Node pred = tail.getAndSet(node);
        myPredecessor.set(pred);

        // --- THE PARKING LOOP (AQS Style) ---
        // We guard the park operation inside a loop to protect against spurious wakeups
        while (pred.isLocked) {
            // Instead of spinning and wasting CPU, we safely put the thread to sleep.
            // The OS takes this thread off the active scheduling queue.
            LockSupport.park(this);
        }

        // Clean up the node's thread reference once the lock is acquired
        node.thread = null;
    }

    /**
     * Releases the lock and explicitly unparks the next thread in line.
     */
    public void unlock() {
        Node node = myNode.get();

        // 1. Flip the status flag to false
        node.isLocked = false;

        // 2. Locate the next thread waiting on us.
        // Because a standard CLH queue is implicitly linked backwards (we only know our predecessor),
        // we have to check if a thread has attached itself to our node yet.
        Thread nextThread = node.thread;

        // 3. If there is a successor thread waiting on our node, wake them up!
        if (nextThread != null) {
            LockSupport.unpark(nextThread);
        }

        // Recycle the predecessor node to optimize memory allocation
        myNode.set(myPredecessor.get());
    }
}
