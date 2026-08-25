package ds.rateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter {

    // Thread-safe map to store rate-limiting buckets per user/client key
    private final Map<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    private final long capacity;
    private final long refillTokensPerSecond;

    /**
     * @param capacity              Maximum tokens the bucket can hold (allows bursts).
     * @param refillTokensPerSecond Rate at which tokens are added back.
     */
    public TokenBucketRateLimiter(long capacity, long refillTokensPerSecond) {
        this.capacity = capacity;
        this.refillTokensPerSecond = refillTokensPerSecond;
    }

    /**
     * Checks if a request is allowed for a given key.
     *
     * @param key Unique identifier (e.g., IP address, user ID).
     * @return true if the request is permitted; false otherwise.
     */
    public boolean allowRequest(String key) {
        // Compute if absent guarantees atomic creation of a bucket per key
        TokenBucket bucket = buckets.computeIfAbsent(key,
                k -> new TokenBucket(capacity, refillTokensPerSecond));
        return bucket.tryConsume();
    }

    // Internal class representing an individual token bucket
    private static class TokenBucket {
        private final long capacity;
        private final long refillRate;

        private double tokens;
        private long lastRefillTimestamp;

        public TokenBucket(long capacity, long refillRate) {
            this.capacity = capacity;
            this.refillRate = refillRate;
            this.tokens = capacity;
            this.lastRefillTimestamp = System.currentTimeMillis();
        }

        /**
         * Lazily refills tokens and tries to consume exactly 1 token.
         * Synchronized to guarantee absolute thread safety for this bucket.
         */
        public synchronized boolean tryConsume() {
            refill();

            if (tokens >= 1.0) {
                tokens -= 1.0;
                return true;
            }
            return false;
        }

        private void refill() {
            long now = System.currentTimeMillis();
            long elapsedTime = now - lastRefillTimestamp;

            if (elapsedTime > 0) {
                // Calculate tokens generated during elapsed time
                double tokensToAdd = (elapsedTime * refillRate) / 1000.0;

                // Cap at maximum capacity
                tokens = Math.min(capacity, tokens + tokensToAdd);
                lastRefillTimestamp = now;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Limit: Max burst of 5 requests, refilling at 2 tokens per second
        TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter(5, 2);
        String userId = "user_123";

        // 1. Simulate a burst of requests
        for (int i = 1; i <= 7; i++) {
            boolean allowed = rateLimiter.allowRequest(userId);
            System.out.println("Request " + i + ": " + (allowed ? "ALLOWED" : "REJECTED"));
        }

        // 2. Wait 1 second to let tokens refill
        System.out.println("\nWaiting 1 second...");
        Thread.sleep(1000);

        // 3. Try again after refill
        for (int i = 1; i <= 3; i++) {
            boolean allowed = rateLimiter.allowRequest(userId);
            System.out.println("Post-refill Request " + i + ": " + (allowed ? "ALLOWED" : "REJECTED"));
        }
    }
}
