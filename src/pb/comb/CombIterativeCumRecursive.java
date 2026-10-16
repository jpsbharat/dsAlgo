package pb.comb;

import java.util.ArrayList;
import java.util.List;

public class CombIterativeCumRecursive {

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
        List<List<T>> result = new ArrayList<>();
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

        for(int i = index; i < elements.size(); i++) {
            current.add(elements.get(i));
            combinationWithRepetition(elements, i, current, k, result);
            current.removeLast();
        }
    }

    public static <T> List<List<T>> combinationWithoutRepetition(List<T> elements, int k) {
        List<List<T>> result = new ArrayList<>();
        combinationWithoutRepetition(elements, 0, new ArrayList<>(), k, result);
        return result;
    }

    private static <T> void combinationWithoutRepetition(List<T> elements, int index, List<T> current, int k, List<List<T>> result) {
        if(current.size() == k){
            result.add(new ArrayList<>(current));
            return;
        }
        if(index >= elements.size()){
            return;
        }

        for(int i = index; i < elements.size(); i++) {
            current.add(elements.get(i));
            combinationWithoutRepetition(elements, i + 1, current, k, result);
            current.removeLast();
        }
    }
}
