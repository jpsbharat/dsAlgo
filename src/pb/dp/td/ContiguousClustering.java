package pb.dp.td;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContiguousClustering {

    public static void main(String[] args) {
        List<Integer> data = new ArrayList<>(List.of(4, 12, 5, 2, 8, 20, 22, 19));
        int k = 3;

        ClusteringResult result = optimizeContiguousTopDown(data, k);

        System.out.printf("Optimized Cluster Cost (WCSS): %.2f\n", result.minCost);
        System.out.println("Contiguous Clusters (Original Order):");
        for (int i = 0; i < result.clusters.size(); i++) {
            System.out.println("  Cluster " + (i + 1) + ": " + result.clusters.get(i));
        }
    }

    private static class MemoState {
        double minCost;
        int bestSplit; // Stores the index where the next cluster should start

        MemoState(double minCost, int bestSplit) {
            this.minCost = minCost;
            this.bestSplit = bestSplit;
        }
    }

    public static class ClusteringResult {
        public double minCost;
        public List<List<Integer>> clusters;

        public ClusteringResult(double minCost, List<List<Integer>> clusters) {
            this.minCost = minCost;
            this.clusters = clusters;
        }
    }

    public static ClusteringResult optimizeContiguousTopDown(List<Integer> data, int k) {
        if (data == null || data.isEmpty() || k <= 0) {
            return new ClusteringResult(0.0, new ArrayList<>());
        }

        int n = data.size();
        if (k >= n) {
            List<List<Integer>> individualClusters = new ArrayList<>();
            for (Integer num : data) {
                individualClusters.add(new ArrayList<>(List.of(num)));
            }
            return new ClusteringResult(0.0, individualClusters);
        }

        // HashMap to memoize: Key = "index,remK", Value = MemoState
        Map<String, MemoState> memo = new HashMap<>();

        // Start recursion from index 0 with k clusters remaining
        MemoState finalState = computeDP(data, 0, k, memo);

        // Reconstruct the clusters forwards using the saved split indices
        List<List<Integer>> clusters = new ArrayList<>();
        int currentStart = 0;
        int remK = k;

        while (remK > 0) {
            String key = currentStart + "," + remK;
            int nextStart = memo.get(key).bestSplit;

            clusters.add(new ArrayList<>(data.subList(currentStart, nextStart)));
            currentStart = nextStart;
            remK--;
        }

        return new ClusteringResult(finalState.minCost, clusters);
    }

    private static MemoState computeDP(List<Integer> data, int index, int remK, Map<String, MemoState> memo) {
        int n = data.size();
        String key = index + "," + remK;

        // 1. Check Cache
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        // 2. Base Case: Last cluster must swallow all remaining elements
        if (remK == 1) {
            double cost = getClusterCost(data, index, n - 1);
            MemoState state = new MemoState(cost, n); // Split point is 'n' (end of list)
            memo.put(key, state);
            return state;
        }

        double minCost = Double.MAX_VALUE;
        int bestSplit = -1;

        // 3. Try all valid contiguous chunk sizes for the current cluster
        // The current cluster ends at 'i - 1', and the next subproblem starts at index 'i'
        // We leave enough elements at the end to satisfy the remaining clusters (n - i >= remK - 1)
        for (int i = index + 1; i <= n - (remK - 1); i++) {
            double currentClusterCost = getClusterCost(data, index, i - 1);

            // Recurse for the remaining clusters starting at index 'i'
            MemoState nextSubProblem = computeDP(data, i, remK - 1, memo);

            double totalCost = currentClusterCost + nextSubProblem.minCost;

            if (totalCost < minCost) {
                minCost = totalCost;
                bestSplit = i;
            }
        }

        MemoState resultState = new MemoState(minCost, bestSplit);
        memo.put(key, resultState);
        return resultState;
    }

    /**
     * Calculates the Sum of Squared Deviations (Variance Cost) for a contiguous sublist.
     */
    private static double getClusterCost(List<Integer> data, int start, int end) {
        if (start >= end) return 0.0;

        double sum = 0;
        for (int i = start; i <= end; i++) {
            sum += data.get(i);
        }
        double mean = sum / (end - start + 1);

        double varianceCost = 0;
        for (int i = start; i <= end; i++) {
            varianceCost += Math.pow(data.get(i) - mean, 2);
        }

        return varianceCost;
    }
}
