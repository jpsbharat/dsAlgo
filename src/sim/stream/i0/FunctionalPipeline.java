package sim.stream.i0;

import java.util.function.Function;

public class FunctionalPipeline {
    public static void main(String[] args) {
        // 1. Define individual stages using Lambdas
        Function<String, Integer> stage1 = String::length;
        Function<Integer, Double> stage2 = len -> len * 2.5;
        Function<Double, String> stage3 = val -> "Result: " + val;

        // 2. Compose the pipeline natively
        Function<String, String> pipeline = stage1.andThen(stage2).andThen(stage3);

        // 3. Execute
        String finalOutput = pipeline.apply("Hello World");
        System.out.println(finalOutput); // Output: Result: 27.5
    }
}
