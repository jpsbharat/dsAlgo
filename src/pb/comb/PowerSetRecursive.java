package pb.comb;

import java.util.ArrayList;
import java.util.List;

public class PowerSetRecursive {

    public static void test(List<String> elements) {
        System.out.println("PowerSet:");
        List<List<String>> powerSet = powerSet(elements);
        System.out.println(powerSet);
        System.out.println();
        System.out.println("PowerSet Permutations:");
        powerSet = powerSetPermutations(elements);
        System.out.println(powerSet);
        System.out.println();
    }

    public static void main(String[] args) {
        // Example usage
        List<String> elements = List.of("A", "B", "C");
        test(elements);
    }

    public static <T> List<List<T>> powerSet(List<T> elems) {
        List<List<T>> result = new ArrayList<>();
        powerSet(elems, 0, new ArrayList<>(), result);
        return result;
    }

    private static <T> void powerSet(List<T> elems, int index, List<T> current, List<List<T>> result) {
        if (index >= elems.size()) {
            result.add(new ArrayList<>(current));
            return;
        }
        // Include the current element
        current.add(elems.get(index));
        powerSet(elems, index + 1, current, result);
        current.removeLast();

        // Exclude the current element
        powerSet(elems, index + 1, current, result);
    }

    public static <T> List<List<T>> powerSetPermutations(List<T> elems) {
        List<List<T>> result = new ArrayList<>();
        boolean[] processed = new boolean[elems.size()];
        powerSetPermutations(elems, 0, processed, new ArrayList<>(), result);
        return result;
    }

    private static <T> void powerSetPermutations(List<T> elems, int index, boolean[] processed, List<T> current, List<List<T>> result) {
        if (index >= elems.size()) {
            result.add(new ArrayList<>(current));
            return;
        }

        if (!processed[index]) {
            // Include the current element
            current.add(elems.get(index));
            processed[index] = true;
            powerSetPermutations(elems, 0, processed, current, result);
            current.removeLast();
            processed[index] = false;
        }

        // Exclude the current element
        powerSetPermutations(elems, index + 1, processed, current, result);
    }
}
