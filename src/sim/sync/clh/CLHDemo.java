package sim.sync.clh;

public class CLHDemo {
    private static int sharedCounter = 0;

    public static void main(String[] args) throws InterruptedException {
        testLock(createTaskWithCLHSpinLock(new CLHSpinLock(), 1000), 5 * 1000, 5 * 1000);
        testLock(createTaskWithCLHParkableLock(new CLHParkableLock(), 1000), 5 * 1000, 5 * 1000);
    }

    public static void testLock(final Runnable task, int noOfThreads, int expectedValue) throws InterruptedException {
        sharedCounter = 0; // Reset counter for testing
        Thread[] threads = new Thread[noOfThreads];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(task, "TestWorker-" + i);
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        if (sharedCounter != expectedValue) {
            throw new AssertionError("Test failed! Expected " + expectedValue + " but got " + sharedCounter);
        } else {
            System.out.println("Test passed! Final Counter Value: " + sharedCounter);
        }
    }

    public static Runnable createTaskWithCLHSpinLock(final CLHSpinLock lock, int numberOfIterations) {
        return () -> {
            for (int i = 0; i < numberOfIterations; i++) {
                lock.lock();
                try {
                    sharedCounter++; // Critical Section
                } finally {
                    lock.unlock();
                }
            }
        };
    }

    public static Runnable createTaskWithCLHParkableLock(final CLHParkableLock lock, int numberOfIterations) {
        return () -> {
            for (int i = 0; i < numberOfIterations; i++) {
                lock.lock();
                try {
                    sharedCounter++; // Critical Section
                } finally {
                    lock.unlock();
                }
            }
        };
    }
}
