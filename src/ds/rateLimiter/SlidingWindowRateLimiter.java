package ds.rateLimiter;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

// Implements a thread-safe sliding window rate limiter
public class SlidingWindowRateLimiter {
    private final Map<String, UserWindow> userWindows = new ConcurrentHashMap<>();
    private final int maxRequests;
    private final long windowSizeInMs;

    public SlidingWindowRateLimiter(int maxRequests, long windowSizeInMs) {
        this.maxRequests = maxRequests;
        this.windowSizeInMs = windowSizeInMs;
    }

    public boolean shouldAllow(String userId) {
        long currentTimestamp = System.currentTimeMillis();
        // ComputeIfAbsent ensures thread-safe initialization per user
        UserWindow window = userWindows.computeIfAbsent(userId, k -> new UserWindow(maxRequests, windowSizeInMs));
        return window.tryAllow(currentTimestamp);
    }

    private static class UserWindow {
        private final Queue<Long> requestTimestamps = new ArrayDeque<>();
        private final int maxRequests;
        private final long windowSizeInMs;

        UserWindow(int maxRequests, long windowSizeInMs) {
            this.maxRequests = maxRequests;
            this.windowSizeInMs = windowSizeInMs;
        }

        // Synchronized per user to ensure thread safety
        public synchronized boolean tryAllow(long currentTimestamp) {
            long windowStartBoundary = currentTimestamp - windowSizeInMs;
            // Remove timestamps older than the current window
            while (!requestTimestamps.isEmpty() && requestTimestamps.peek() <= windowStartBoundary) {
                requestTimestamps.poll();
            }
            if (requestTimestamps.size() < maxRequests) {
                requestTimestamps.add(currentTimestamp);
                return true;
            }
            return false;
        }
    }
}

