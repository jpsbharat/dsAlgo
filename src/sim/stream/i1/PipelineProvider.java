package sim.stream.i1;

public class PipelineProvider {

    public static class StringLengthStage implements Pipeline.Stage<String, Integer> {

        @Override
        public Integer process(String input) {
            return input != null ? input.length() : 0;
        }
    }

    // Stage 2: Takes Integer, returns Double (multiplies it)
    public static class MultiplierStage implements Pipeline.Stage<Integer, Double> {

        @Override
        public Double process(Integer input) {
            return input * 2.5;
        }
    }

    // Stage 3: Takes Double, returns String formatted text
    public static class FormatStage implements Pipeline.Stage<Double, String> {

        @Override
        public String process(Double input) {
            return "Final Result: $" + input;
        }
    }
}
