package sim.stream.i3;

import java.util.List;

public class CustomStreamDemo {

    public static void main(String[] args) {
        List<String> names = List.of("apple", "banana", "cherry");

        // Pipeline:
        // 1. Get string lengths (Map String -> Integer)
        // 2. Double those lengths (Map Integer -> Integer)
        // 3. Sum them all up starting at 0 (Reduce)
        int totalLengthSum = CustomStream.of(names)
                .map(String::length)            // [5, 6, 6]
                .map(len -> len * 2)            // [10, 12, 12]
                .reduce(0, Integer::sum);       // 10 + 12 + 12 = 34

        System.out.println("Total calculated score: " + totalLengthSum);
        // Output: Total calculated score: 34
    }
}
