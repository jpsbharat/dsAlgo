package pb.perm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutation {

    public static void main(String[] args) {
        List<Integer> input = Arrays.asList(1, 2, 3, 4);
        List<List<Integer>> p = permute(input);
        System.out.println("Size=" + p.size() + ", P=" + p);
        p.forEach(e -> System.out.println(e));
    }

    public static <T> List<List<T>> permute(List<T> input) {
        List<Integer> visitedIndices = new ArrayList<>();
        input.stream().forEach(e -> visitedIndices.add(0));
        List<List<T>> result = new ArrayList<>();
        permute(input, 0, input.size(), result);
        return result;
    }

    public static <T> void permute(List<T> input, int index, int k,
                                   List<List<T>> result) {
        if (index == k) {
            result.add(new ArrayList<>(input));
        } else {
            int i;
            for (i = index; i < input.size(); i++) {
                T tmp = input.get(i);
                input.set(i, input.get(index));
                input.set(index, tmp);
                permute(input, index + 1, k, result);
                tmp = input.get(i);
                input.set(i, input.get(index));
                input.set(index, tmp);
            }
        }
    }

}
