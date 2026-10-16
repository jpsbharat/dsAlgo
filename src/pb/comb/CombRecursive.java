package pb.comb;

import java.util.ArrayList;
import java.util.List;

public class CombRecursive {
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
        List<List<T>> result = new java.util.ArrayList<>();
        combinationWithRepetition(elements, 0, new ArrayList<>(), k, result);
        return result;
    }

    private static <T> void combinationWithRepetition(List<T> elements, int index, List<T> current, int k, List<List<T>> result) {
        if(current.size() == k){
            result.add(new ArrayList<>(current));
            return;
        }
        if(index >= elements.size()){
            return;
        }
        // Include the current element
        current.add(elements.get(index));
        combinationWithRepetition(elements, index, current, k, result);
        current.remove(current.size() - 1);
        // Exclude the current element
        combinationWithRepetition(elements, index + 1, current, k, result);
    }

    public static <T> List<List<T>> combinationWithoutRepetition(List<T> elements, int k) {
        List<List<T>> result = new java.util.ArrayList<>();
        combinationWithoutRepetition(elements, 0, new ArrayList<>(), k, result);
        return result;
    }

    private static <T> void combinationWithoutRepetition(List<T> elements, int index, List<T> current, int k, List<List<T>> result) {
        if(current.size() == k){
            result.add(new java.util.ArrayList<>(current));
            return;
        }
        if(index >= elements.size()){
            return;
        }
        // Include the current element
        current.add(elements.get(index));
        combinationWithoutRepetition(elements, index + 1, current, k, result);
        current.remove(current.size() - 1);
        // Exclude the current element
        combinationWithoutRepetition(elements, index + 1, current, k, result);
    }
}
