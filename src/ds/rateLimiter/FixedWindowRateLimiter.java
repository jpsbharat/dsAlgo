package ds.rateLimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowRateLimiter {
    private final int maxRequests;
    private final long windowSizeMillis;
    private final ConcurrentHashMap<String, WindowCounter> userCache = new ConcurrentHashMap<>();

    public FixedWindowRateLimiter(int maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
    }

    public boolean tryAcquire(String clientId) {
        long currentTime = System.currentTimeMillis();
        // Calculate the exact start boundary of the current fixed window
        long currentWindowStart = (currentTime / windowSizeMillis) * windowSizeMillis;

        // Atomically fetch or initialize the window counter for the client
        WindowCounter counter = userCache.compute(clientId, (key, existingWindow) -> {
            if (existingWindow == null || existingWindow.windowStart != currentWindowStart) {
                return new WindowCounter(currentWindowStart, new AtomicInteger(0));
            }
            return existingWindow;
        });

        // Increment and evaluate against the request limit
        int currentRequests = counter.requestCount.incrementAndGet();
        return currentRequests <= maxRequests;
    }

    private static class WindowCounter {
        final long windowStart;
        final AtomicInteger requestCount;

        WindowCounter(long windowStart, AtomicInteger requestCount) {
            this.windowStart = windowStart;
            this.requestCount = requestCount;
        }
    }
}
