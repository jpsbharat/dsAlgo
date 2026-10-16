package pb.comb;

import java.util.ArrayList;
import java.util.List;

public class PowerSetIterativeCumRecursive {

    public static void test(List<String> elements) {
        System.out.println("PowerSet Ordered:");
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
        result.add(new ArrayList<>(current));

        for (int i = index; i < elems.size(); i++) {
            current.add(elems.get(i));
            powerSet(elems, i + 1, current, result);
            current.removeLast();
        }
    }

    public static <T> List<List<T>> powerSetPermutations(List<T> elems) {
        List<List<T>> result = new ArrayList<>();
        boolean[] processed = new boolean[elems.size()];
        powerSetPermutations(elems, processed, new ArrayList<>(), result);
        return result;
    }

    private static <T> void powerSetPermutations(List<T> elems, boolean[] processed, List<T> current, List<List<T>> result) {
        result.add(new ArrayList<>(current));

        for (int i = 0; i < elems.size(); i++) {
            if (!processed[i]) {
                current.add(elems.get(i));
                processed[i] = true;
                powerSetPermutations(elems, processed, current, result);
                current.removeLast();
                processed[i] = false;
            }
        }
    }
}
