package sim.pipeline.wave;

import java.util.Iterator;

/**
 * Represents a point in a wave-based data processing system that can receive and transmit vibrations.
 * @param <R> Vibration received from upstream point
 * @param <T> Vibration transmitted to downstream point
 */
public abstract class Point<R, T> {
    private final Iterator<T> vibrator;
    protected Point<?, R> prevPoint;
    protected Point<T, ?> nextPoint;

    // It creates the source of the wave
    public Point(Iterator<T> vibrator){
        this.vibrator = vibrator;
        this.prevPoint = null;
        this.nextPoint = null;
    }

    // It creates an intermediary point in the wave.
    public Point(final Point<?, R> prevPoint){
        this.vibrator = null;
        this.prevPoint = prevPoint;
        this.prevPoint.nextPoint = this;
    }

    public Iterator<T> getVibrator() {
        return vibrator;
    }

    @Override
    public String toString() {
        return "Point(" + this.hashCode() + ") -> [P(" + ((prevPoint == null) ? null : prevPoint.hashCode()) + "), N(" + ((nextPoint == null) ? null : nextPoint) + ")]";
    }

    /**
     * It connects this point to a downstream point, allowing the transmission of vibrations from this point to the downstream point.
     * @param transmitted It is the transmitted vibration containing data of type T
     * @return It returns a vibration of type R received from the upstream point to be processed by this point and transmitted to the downstream point.
     */
    public abstract Vibration<R> vibrate(Vibration<T> transmitted);
}
