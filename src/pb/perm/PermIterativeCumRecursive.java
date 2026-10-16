package pb.perm;

import java.util.ArrayList;
import java.util.List;

public class PermIterativeCumRecursive {

    public static void test(List<String> elements, int k) {
        System.out.println("Permutations With Repetition:");
        List<List<String>> result1 = permutationWithRepetition(elements, k);
        System.out.println(result1);
        System.out.println();

        System.out.println("Permutations Without Repetition:");
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
        List<List<T>> result = new java.util.ArrayList<>();
        permutationWithRepetition(elements, new ArrayList<>(), k, result);
        return result;
    }

    private static <T> void permutationWithRepetition(List<T> elements, List<T> current, int k, List<List<T>> result) {
        if (current.size() == k) {
            result.add(new java.util.ArrayList<>(current));
            return;
        }

        for (T elem : elements) {
            current.add(elem);
            permutationWithRepetition(elements, current, k, result);
            current.removeLast();
        }
    }

    public static <T> List<List<T>> permutationWithoutRepetition(List<T> elements, int k) {
        List<List<T>> result = new ArrayList<>();
        permutationWithoutRepetition(elements, new ArrayList<>(), k, new boolean[elements.size()], result);
        return result;
    }

    private static <T> void permutationWithoutRepetition(List<T> elements, List<T> current, int k, boolean[] processed, List<List<T>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < elements.size(); i++) {
            if (!processed[i]) {
                T elem = elements.get(i);
                current.add(elem);
                processed[i] = true;
                permutationWithoutRepetition(elements, current, k, processed, result);
                current.removeLast();
                processed[i] = false;
            }
        }
    }
}
