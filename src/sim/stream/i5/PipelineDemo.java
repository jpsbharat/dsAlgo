package sim.stream.i5;

import java.util.List;

public class PipelineDemo {

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4);

        System.out.println("--- Constructing Operations AST (Lazy Verification) ---");
        Pipeline<String> stream = Pipeline.from(numbers)
                .filter(n -> {
                    System.out.println("Executing Filter on: " + n);
                    return n % 2 == 0;
                })
                .map(n -> {
                    System.out.println("Executing Map on: " + n);
                    return "Value-" + (n * 10);
                });

        System.out.println("\n--- Triggering Terminal Operation (Eager Push Engine) ---");
        String reduction = stream.reduce("", (accum, str) -> accum + "[" + str + "] ");

        System.out.println("\nFinal Output: " + reduction);
        // Expected Output: Final Output: [Value-20] [Value-40]
    }
}
