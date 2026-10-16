package pb.comb;

import java.util.ArrayList;
import java.util.List;

public class PowerSetIterative {

    public static void test(List<String> elements) {
        List<List<String>> powerSet = powerSet(elements);
        System.out.println("PowerSet Ordered: " + powerSet.size());
        System.out.println(powerSet);
        System.out.println();
        powerSet = powerSetPermutations(elements);
        System.out.println("PowerSet Permutations: " + powerSet.size());
        System.out.println(powerSet);
        System.out.println();
        powerSet = powerSetPermutationsWithRepetition(elements);
        System.out.println("PowerSet Permutations with Repetition: " + powerSet.size());
        System.out.println(powerSet);
        System.out.println();
    }

    public static void main(String[] args) {
        // Example usage
        List<String> elements = List.of("A", "B", "C");
        test(elements);
    }

    public static <T> List<List<T>> powerSet(List<T> elems) {
        int n = elems.size();
        List<List<T>> result = new ArrayList<>();
        int[] indices = new int[n];
        while (true) {
            copy(elems, indices, result);

            int i = n - 1;
            while (i >= 0) {
                indices[i]++;
                if (indices[i] < 2) {
                    break;
                }
                indices[i] = 0;
                i--;
            }
            if (i < 0) break;
        }
        return result;
    }

    public static <T> List<List<T>> powerSetPermutations(List<T> elems) {
        List<List<T>> result = new ArrayList<>();
        for(int k = 0; k <= elems.size(); k++) {
            int[] indices = new int[k];
            while (true) {
                if(unique(indices, elems.size())) {
                    copy(elems, indices, k, result);
                }

                int i = k - 1;
                while (i >= 0) {
                    indices[i]++;
                    if (indices[i] < elems.size()) {
                        break;
                    }
                    indices[i] = 0;
                    i--;
                }
                if (i < 0) break;
            }

        }
        return result;
    }

    public static <T> List<List<T>> powerSetPermutationsWithRepetition(List<T> elems) {
        List<List<T>> result = new ArrayList<>();
        for(int k = 0; k <= elems.size(); k++) {
            int[] indices = new int[k];
            while (true) {
                copy(elems, indices, k, result);

                int i = k - 1;
                while (i >= 0) {
                    indices[i]++;
                    if (indices[i] < elems.size()) {
                        break;
                    }
                    indices[i] = 0;
                    i--;
                }
                if (i < 0) break;
            }

        }
        return result;
    }

    private static <T> void copy(List<T> elements, int[] indices, List<List<T>> result) {
        List<T> currentCombination = new ArrayList<>();
        for (int i = 0; i < indices.length; i++) {
            if (indices[i] == 1) {
                currentCombination.add(elements.get(i));
            }
        }
        result.add(currentCombination);
    }

    private static <T> void copy(List<T> elements, int[] indices, int k, List<List<T>> result) {
        List<T> currentCombination = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            currentCombination.add(elements.get(indices[i]));
        }
        result.add(currentCombination);
    }

    private static boolean unique(int[] indices, int n) {
        boolean[] processed = new boolean[n];
        for (int idx : indices) {
            if (processed[idx]) return false;
            processed[idx] = true;
        }
        return true;
    }
}
