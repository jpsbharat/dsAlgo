package ds.rateLimiter;

import java.util.concurrent.*;

public class LeakyBucketLimiter {
    private final BlockingQueue<Runnable> bucket;
    private final ScheduledExecutorService leakScheduler;

    public LeakyBucketLimiter(int bucketCapacity, long leakIntervalMillis) {
        // The bucket size restricts how many requests can wait in line
        this.bucket = new LinkedBlockingQueue<>(bucketCapacity);
        this.leakScheduler = Executors.newSingleThreadScheduledExecutor();

        // Start the continuous background leak process
        this.leakScheduler.scheduleAtFixedRate(this::leak, 0, leakIntervalMillis, TimeUnit.MILLISECONDS);
    }

    public boolean tryAcquire(Runnable requestTask) {
        // Try to add the request to the bucket queue. Returns false immediately if full.
        return bucket.offer(requestTask);
    }

    private void leak() {
        Runnable task = bucket.poll();
        if (task != null) {
            // Process the request task at our steady leak rate
            try {
                task.run();
            } catch (Exception e) {
                System.err.println("Task execution failed: " + e.getMessage());
            }
        }
    }

    // Call this to clean up resources during application shutdown
    public void shutdown() {
        leakScheduler.shutdown();
    }
}
