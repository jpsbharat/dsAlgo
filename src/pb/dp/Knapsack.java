package pb.dp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Knapsack {
    private final Map<MemoKey, Integer> memo = new HashMap<>();

    public static void main(String[] args) {
        int knapsackMaxWeight = 5;
        int[] values = {200, 240, 140, 150};
        int[] weights = {1, 3, 2, 5};

        int maxProfit = maxProfit(values, weights, knapsackMaxWeight);
        System.out.println(maxProfit);
    }

    public static int maxProfit(int[] values, int[] weights, int weight) {
        Knapsack knapsack = new Knapsack();
        return knapsack.maxProfit(values.length - 1, weight, values, weights);
    }

    public int maxProfit(int i, int w, int[] values, int[] weights) {
        if (i < 0 || w <= 0) {
            return 0;
        }

        MemoKey key = new MemoKey(i, w);
        Integer savedValue = memo.get(key);
        if (savedValue != null) {
            return savedValue;
        }

        int exclude = maxProfit(i - 1, w, values, weights);
        int include = 0;
        if (weights[i] <= w) {
            include = values[i] + maxProfit(i - 1, w - weights[i], values, weights);
        }

        int maxProfit = Math.max(include, exclude);
        memo.put(key, maxProfit);
        return maxProfit;
    }

    private static class MemoKey {
        private final int index;
        private final int weight;

        public MemoKey(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            MemoKey memoKey = (MemoKey) o;
            return index == memoKey.index && weight == memoKey.weight;
        }

        @Override
        public int hashCode() {
            return Objects.hash(index, weight);
        }
    }
}
