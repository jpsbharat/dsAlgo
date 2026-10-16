package pb.comb;

import java.util.ArrayList;
import java.util.List;

public class CombIterative {

    public static void test(List<String> elements, int k) {
        System.out.println("Combination With Repetition:");
        List<List<String>> result1 = combinationWithRepetition(elements, k);
        System.out.println(result1);
        System.out.println();

        System.out.println("Combination Without Repetition:");
        List<List<String>> result2 = combinationWithoutRepetition(elements, k);
        System.out.println(result2);
        System.out.println();
    }

    public static void main(String[] args) {
        // Example usage
        List<String> elements = List.of("A", "B", "C");
        test(elements, 2);
        test(elements, 3);
    }

    public static <T> List<List<T>> combinationWithRepetition(List<T> elements, int k) {
        int n = elements.size();
        List<List<T>> result = new ArrayList<>();
        int[] indices = new int[k];
        while (true) {
            copy(elements, indices, k, result);

            int i = k - 1;
            while (i >= 0 && indices[i] == n - 1) {
                i--;
            }
            if (i < 0) break;
            indices[i]++;
            // Ensuring strictly non-decreasing indices to allow repetition
            for (int j = i + 1; j < k; j++) {
                indices[j] = i;
            }
        }
        return result;
    }

    public static <T> List<List<T>> combinationWithoutRepetition(List<T> elements, int k) {
        int n = elements.size();
        List<List<T>> result = new ArrayList<>();
        int[] indices = new int[k]; // Strictly increasing indices to avoid repetition
        for (int i = 0; i < k; i++) {
            indices[i] = i;
        }
        while (true) {
            copy(elements, indices, k, result);

            int i = k - 1;
            while (i >= 0 && indices[i] == n - k + i) {
                i--;
            }
            if (i < 0) break;
            indices[i]++;
            // Ensuring strictly increasing indices to avoid repetition
            for (int j = i + 1; j < k; j++) {
                indices[j] = indices[j - 1] + 1;
            }
        }
        return result;
    }

    private static <T> void copy(List<T> elements, int[] indices, int k, List<List<T>> result) {
        List<T> currentCombination = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            currentCombination.add(elements.get(indices[i]));
        }
        result.add(currentCombination);
    }
}
