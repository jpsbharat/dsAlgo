package sim.pipeline.windUpKeyToy;

/**
 * Toy interface represents a toy that can be wound up with energy and released.
 *
 * @param <T> the type of energy used to wind up the toy, which must extend Integer
 */
public interface Toy<T extends Integer> {
    Toy<T> key(T energyConsumer);

    void release();
}
