package pb.dp;

import java.util.*;

public class SubsetSumDP {
    private static final Map<MemoKey, Integer> memo = new HashMap<>();

    public static List<List<Integer>> subsetSum(List<Integer> input, int targetSum) {
        List<List<Integer>> combinations = new ArrayList<>();
        subsetSum(input, 0, new ArrayList<>(), 0, targetSum, combinations);
        return combinations;
    }

    public static void subsetSum(List<Integer> input, int index, List<Integer> currSelection, int currentSum, int targetSum, List<List<Integer>> combinations) {
        if (index >= input.size()) {
            return;
        }

        if (targetSum == currentSum) {
            combinations.add(new ArrayList<>(currSelection));
        }

        MemoKey key = new MemoKey(currentSum, currSelection);
        Integer savedValue = memo.get(key);
        if (savedValue != null) {
            // return savedValue;
        }
        currSelection.add(index);
        subsetSum(input, index + 1, currSelection, currentSum + input.get(index), targetSum, combinations);
        currSelection.removeLast();
        subsetSum(input, index + 1, currSelection, currentSum, targetSum, combinations);
    }

    private static class MemoKey {
        private final int sum;
        private final List<Integer> indices;

        public MemoKey(int sum, List<Integer> indices) {
            this.sum = sum;
            this.indices = new ArrayList<>(indices);
            Collections.sort(this.indices);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            MemoKey memoKey = (MemoKey) o;
            return sum == memoKey.sum && Objects.equals(indices, memoKey.indices);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sum, indices);
        }
    }
}
