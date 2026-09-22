package sim.pipeline.stream.i1;

public class PipelineDemo {

    public static void main(String[] args) {
        // Build the pipeline seamlessly across varying types
        Pipeline<String, String> executionPipeline = new Pipeline<>(new PipelineProvider.StringLengthStage())
                .addHandler(new PipelineProvider.MultiplierStage())
                .addHandler(new PipelineProvider.FormatStage());

        String result = executionPipeline.execute("Hello World");
        System.out.println(result); // Output: Final Result: $27.5
    }
}
