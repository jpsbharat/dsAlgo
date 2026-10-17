package pb.dp.td;

import java.util.*;

public class SectorPartitioningInCluster {

    private static void test(List<Integer> sectorCosts, int k) {
        System.out.println("Input Sector Cost Array: " + sectorCosts);
        System.out.println("Target Number of Clusters (k): " + k);
        System.out.println("=======================================================\n");

        System.out.println("--- Contiguous Minimization (First + Last Element Cost Criteria) ---");
        List<List<Integer>> contiguousResult = findMinCostSector(sectorCosts, k);
        printClusterMetrics(contiguousResult);
        contiguousResult = findMaxCostSector(sectorCosts, k);
        printClusterMetrics(contiguousResult);
    }

    public static void main(String[] args) {
        // Sector metrics represent direct operational costs
        List<Integer> sectorCosts = Arrays.asList(45, 12, 89, 34, 67, 23, 56, 78);
        test(sectorCosts, 3);
        sectorCosts = Arrays.asList(1, 2, 3, 2, 5);
        test(sectorCosts, 3);
    }

    public static List<List<Integer>> findMinCostSector(List<Integer> sectorCosts, int k) {
        int n = sectorCosts.size();
        if (n == 0 || k <= 0) return new ArrayList<>();

        // Edge case: If clusters exceed or equal elements, isolate each element
        if (k >= n) {
            return createPartitionForExceedingK(sectorCosts, k);
        }

        // Caches using primitive primitive-packed Long keys
        Map<Long, Long> memo = new HashMap<>();
        Map<Long, Integer> splitTracker = new HashMap<>();

        // Invoke recursive solver targeting the structural sector costs list
        findMinCost(0, k, n, sectorCosts, memo, splitTracker);
        return createPartition(sectorCosts, splitTracker, k);
    }

    public static List<List<Integer>> findMaxCostSector(List<Integer> sectorCosts, int k) {
        int n = sectorCosts.size();
        if (n == 0 || k <= 0) return new ArrayList<>();

        // Edge case: If clusters exceed or equal elements, isolate each element
        if (k >= n) {
            return createPartitionForExceedingK(sectorCosts, k);
        }

        // Caches using primitive primitive-packed Long keys
        Map<Long, Long> memo = new HashMap<>();
        Map<Long, Integer> splitTracker = new HashMap<>();

        // Invoke recursive solver targeting the structural sector costs list
        findMaxCost(0, k, n, sectorCosts, memo, splitTracker);
        return createPartition(sectorCosts, splitTracker, k);
    }

    private static List<List<Integer>> createPartitionForExceedingK(List<Integer> sectorCosts, int k) {
        List<List<Integer>> partitions = new ArrayList<>();
        for (int cost : sectorCosts) {
            partitions.add(new ArrayList<>(List.of(cost)));
        }
        while (partitions.size() < k) partitions.add(new ArrayList<>());
        return partitions;
    }

    private static List<List<Integer>> createPartition(List<Integer> sectorCosts, Map<Long, Integer> splitTracker, int k) {
        int n = sectorCosts.size();
        List<List<Integer>> partitions = new ArrayList<>();
        int currIndex = 0;
        for (int i = k; i > 0; i--) {
            int nextSplit;
            if (i == 1) {
                nextSplit = n; // The last partition absorbs all remaining elements
            } else {
                long stateKey = encodeState(currIndex, i);
                nextSplit = splitTracker.get(stateKey);
            }

            List<Integer> cluster = new ArrayList<>();
            for (int j = currIndex; j < nextSplit; j++) {
                cluster.add(sectorCosts.get(j));
            }
            partitions.add(cluster);
            currIndex = nextSplit;
        }

        return partitions;
    }

    /**
     * Packs two 32-bit integers (index and remainingK) into a single 64-bit primitive long.
     */
    private static long encodeState(int index, int remainingK) {
        return ((long) index << 32) | (remainingK & 0xFFFFFFFFL);
    }

    private static long findMinCost(int index, int remainingK, int n, List<Integer> sectorCosts,
                                    Map<Long, Long> memo, Map<Long, Integer> splitTracker) {
        // Base Case 1: Final cluster must take everything left over from current index to the end
        if (remainingK == 1) {
            // Cost is: element at index (start) + element at n-1 (end)
            return (long) sectorCosts.get(index) + sectorCosts.get(n - 1);
        }

        // Base Case 2: Out of boundaries (not enough elements left to populate downstream clusters)
        if (n - index < remainingK) {
            return Long.MAX_VALUE;
        }

        // Compute the single primitive key and check lookup cache
        long stateKey = encodeState(index, remainingK);
        if (memo.containsKey(stateKey)) {
            return memo.get(stateKey);
        }

        long minCost = Long.MAX_VALUE;
        int bestSplit = index + 1;

        // Scan valid ranges while preserving room for the remaining (remainingK - 1) bins
        int maxValidSplitIndex = n - (remainingK - 1);
        for (int i = index + 1; i <= maxValidSplitIndex; i++) {
            // Core Change: New optimization criteria metric
            // The cluster spans from 'index' to 'i - 1'.
            long currentClusterCost = (long) sectorCosts.get(index) + sectorCosts.get(i - 1);

            long futureClustersCost = findMinCost(i, remainingK - 1, n, sectorCosts, memo, splitTracker);
            long totalStateCost = currentClusterCost + futureClustersCost;

            if (totalStateCost < minCost) {
                minCost = totalStateCost;
                bestSplit = i;
            }
        }

        // Store state properties safely in the hash structures
        memo.put(stateKey, minCost);
        splitTracker.put(stateKey, bestSplit);

        return minCost;
    }

    private static long findMaxCost(int index, int remainingK, int n, List<Integer> sectorCosts,
                                    Map<Long, Long> memo, Map<Long, Integer> splitTracker) {
        // Base Case 1: Final cluster must take everything left over from current index to the end
        if (remainingK == 1) {
            // Cost is: element at index (start) + element at n-1 (end)
            return (long) sectorCosts.get(index) + sectorCosts.get(n - 1);
        }

        // Base Case 2: Out of boundaries (not enough elements left to populate downstream clusters)
        if (n - index < remainingK) {
            return Long.MIN_VALUE;
        }

        // Compute the single primitive key and check lookup cache
        long stateKey = encodeState(index, remainingK);
        if (memo.containsKey(stateKey)) {
            return memo.get(stateKey);
        }

        long maxCost = Long.MIN_VALUE;
        int bestSplit = index + 1;

        // Scan valid ranges while preserving room for the remaining (remainingK - 1) bins
        int maxValidSplitIndex = n - (remainingK - 1);
        for (int i = index + 1; i <= maxValidSplitIndex; i++) {
            // Core Change: New optimization criteria metric
            // The cluster spans from 'index' to 'i - 1'.
            long currentClusterCost = (long) sectorCosts.get(index) + sectorCosts.get(i - 1);

            long futureClustersCost = findMaxCost(i, remainingK - 1, n, sectorCosts, memo, splitTracker);
            long totalStateCost = currentClusterCost + futureClustersCost;

            if (totalStateCost > maxCost) {
                maxCost = totalStateCost;
                bestSplit = i;
            }
        }

        // Store state properties safely in the hash structures
        memo.put(stateKey, maxCost);
        splitTracker.put(stateKey, bestSplit);

        return maxCost;
    }

    private static void printClusterMetrics(List<List<Integer>> partitions) {
        long totalSystemCost = 0;
        for (int i = 0; i < partitions.size(); i++) {
            List<Integer> cluster = partitions.get(i);
            long cost = 0;
            if (!cluster.isEmpty()) {
                cost = (long) cluster.getFirst() + cluster.getLast();
            }
            totalSystemCost += cost;
            System.out.println("  Cluster " + (i + 1) + ": " + cluster + " | Dynamic Cost (First+Last): " + cost);
        }
        System.out.println("  >> Total Minimal Partition Cost: " + totalSystemCost);
    }
}
