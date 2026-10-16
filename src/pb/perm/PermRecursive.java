package pb.perm;

import java.util.ArrayList;
import java.util.List;

public class PermRecursive {

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
        permutationWithRepetition(elements, 0, new java.util.ArrayList<>(), k, result);
        return result;
    }

    private static <T> void permutationWithRepetition(List<T> elements, int index, List<T> current, int k, List<List<T>> result) {
        if (current.size() == k) {
            result.add(new java.util.ArrayList<>(current));
            return;
        }

        if (index >= elements.size()) {
            return;
        }

        T element = elements.get(index);
        current.add(element);
        permutationWithRepetition(elements, 0, current, k, result);
        current.removeLast();
        permutationWithRepetition(elements, index + 1, current, k, result);
    }

    public static <T> List<List<T>> permutationWithoutRepetition(List<T> elements, int k) {
        List<List<T>> result = new ArrayList<>();
        permutationWithoutRepetition(elements, 0, new ArrayList<>(), k, new boolean[elements.size()], result);
        return result;
    }

    private static <T> void permutationWithoutRepetition(List<T> elements, int index, List<T> current, int k, boolean[] processed, List<List<T>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        if (index >= elements.size()) {
            return;
        }

        if (!processed[index]) {
            T element = elements.get(index);
            current.add(element);
            processed[index] = true;
            permutationWithoutRepetition(elements, 0, current, k, processed, result);
            current.removeLast();
            processed[index] = false;
        }
        permutationWithoutRepetition(elements, index + 1, current, k, processed, result);
    }
}
