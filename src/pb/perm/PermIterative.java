package pb.perm;

import java.util.ArrayList;
import java.util.List;

public class PermIterative {
    public static void test(List<String> elements, int k) {
        System.out.println("Permutation With Repetition:");
        List<List<String>> result1 = permutationWithRepetition(elements, k);
        System.out.println(result1);
        System.out.println();

        System.out.println("Permutation Without Repetition:");
        List<List<String>> result2 = permutationWithoutRepetition(elements, k);
        System.out.println(result2);
        System.out.println();
    }

    public static void main(String[] args) {
        // Example usage
        List<String> elements = List.of("A", "B", "C");
        test(elements, 2);
        test(elements, 3);
    }

    public static <T> List<List<T>> permutationWithRepetition(List<T> elements, int k) {
        int n = elements.size();
        List<List<T>> result = new ArrayList<>();
        int[] indices = new int[k];
        while (true) {
            copy(elements, indices, k, result);

            int i = k - 1;
            while (i >= 0) {
                indices[i]++;
                if(indices[i] < n) {
                    break;
                }
                indices[i] = 0;
                i--;
            }
            if (i < 0) break;
        }
        return result;
    }

    public static <T> List<List<T>> permutationWithoutRepetition(List<T> elements, int k) {
        int n = elements.size();
        List<List<T>> result = new ArrayList<>();
        int[] indices = new int[k];
        while (true) {
            if (unique(indices, n)) {
                copy(elements, indices, k, result);
            }

            int i = k - 1;
            while (i >= 0) {
                indices[i]++;
                if(indices[i] < n) {
                    break;
                }
                indices[i] = 0;
                i--;
            }
            if (i < 0) break;
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

    private static boolean unique(int[] indices, int n) {
        boolean[] used = new boolean[n];
        for (int i : indices) {
            if (used[i]) {
                return false;
            }
            used[i] = true;
        }
        return true;
    }
}
