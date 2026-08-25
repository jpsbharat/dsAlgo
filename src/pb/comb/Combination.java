package pb.comb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combination {
    private static int count = 0;

    public static <T> List<List<T>> allCombination(List<T> input) {
        List<List<T>> combinations = new ArrayList<>();
        allCombination(input, 0, new ArrayList<>(), combinations);
        return combinations;
    }

    public static <T> void allCombination(List<T> input, int index, List<T> currSelection, List<List<T>> combinations) {
        combinations.add(new ArrayList<>(currSelection));
        for (int i = index; i < input.size(); i++) {
            currSelection.add(input.get(i));
            allCombination(input, i + 1, currSelection, combinations);
            System.out.println(currSelection);
            currSelection.removeLast();
        }
    }

    public static <T> List<List<T>> allCombinationRec(List<T> input) {
        List<List<T>> combinations = new ArrayList<>();
        allCombinationRec(input, 0, new ArrayList<>(), combinations, 0);
        return combinations;
    }

    public static <T> void allCombinationRec(List<T> input, int index, List<T> currSelection, List<List<T>> combinations, int recNo) {
        System.out.println("RecNo -> " + recNo + ", index -> " + index + " Entered with -> " + currSelection);
        if (index == input.size()) {
            combinations.add(new ArrayList<>(currSelection));
            System.out.println("RecNo -> " + recNo + ", index -> " + index + " Completed with -> " + currSelection);
            count++;
            return;
        }
        currSelection.add(input.get(index));
        allCombinationRec(input, index + 1, currSelection, combinations, 1);
        currSelection.removeLast();
        allCombinationRec(input, index + 1, currSelection, combinations, 2);
        System.out.println("RecNo -> " + recNo + ", index -> " + index + " Exited with -> " + currSelection);
        count++;
    }


    public static void main(String[] args) {
        List<String> combinations = Arrays.asList("a", "b", "c");
        //System.out.println(allCombination(combinations));
        System.out.println(allCombinationRec(combinations));
    }

}
