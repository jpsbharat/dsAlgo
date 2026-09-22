package sim.pipeline.wave;

import java.util.List;

public class WaveDemo {
    public static void main(String[] args) {
        Wave.of(List.of(1))
                .propagate()
                .modulate(x -> x * 2)
                .generate(1);
    }

    public static void testWave() {
        Wave.of(List.of(1, 2, 3, 4, 5))
                .propagate()
                .modulate(x -> x * 2)
                .propagate()
                .modulate(x -> x + 3)
                .propagate()
                .generate(1);
    }
}
