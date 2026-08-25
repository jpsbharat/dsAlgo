package pb.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubsetSum {

    public static void main(String[] args) {
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(subsetSum(input, 9));
    }

    public static List<List<Integer>> subsetSum(List<Integer> input, int targetSum) {
        List<List<Integer>> combinations = new ArrayList<>();
        subsetSum(input, 0, new ArrayList<>(), 0, targetSum, combinations);
        return combinations;
    }

    public static void subsetSum(List<Integer> input, int index, List<Integer> currSelection, int currentSum, int targetSum, List<List<Integer>> combinations) {
        if (targetSum == currentSum) {
            combinations.add(new ArrayList<>(currSelection));
            return;
        }
        if (index >= input.size()) {
            return;
        }
        currSelection.add(input.get(index));
        subsetSum(input, index + 1, currSelection, currentSum + input.get(index), targetSum, combinations);
        currSelection.removeLast();
        subsetSum(input, index + 1, currSelection, currentSum, targetSum, combinations);
    }
}
