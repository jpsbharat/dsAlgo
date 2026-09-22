package sim.pipeline.wave;

import java.util.Iterator;
import java.util.function.Function;

public class Wave<T> {
    private static int count;
    private final Point<?, T> prevPoint;

    private Wave(Iterator<T> vibrator) {
        final int id = count++;
        this.prevPoint = new Point<T, T>(vibrator) {
            @Override
            public Vibration<T> vibrate(Vibration<T> transmitted) {
                return new Vibration<T>() {
                    @Override
                    public void happen(T effect) {
                        System.out.println("Point[" + id + "]" + " starts generating vibration -> " + effect);
                        transmitted.happen(effect);
                        System.out.println("Point[" + id + "]" + " finished vibration");
                    }
                };
            }
        };
    }

    private Wave(Point<?, T> prevPoint) {
        this.prevPoint = prevPoint;
    }

    public static <T> Wave<T> of(Iterable<T> source) {
        return new Wave<>(source.iterator());
    }

    public Wave<T> propagate() {
        final int id = count++;
        Point<T, T> propagateNode = new Point<T, T>(prevPoint) {
            @Override
            public Vibration<T> vibrate(Vibration<T> transmitted) {
                return new Vibration<T>() {
                    @Override
                    public void happen(T effect) {
                        System.out.println("Point[" + id + "]" + " starts vibrating -> " + effect);
                        transmitted.happen(effect);
                        System.out.println("Point[" + id + "]" + " finished vibration");
                    }
                };
            }
        };
        return new Wave<>(propagateNode);
    }

    public <R> Wave<R> modulate(Function<? super T, ? extends R> mapper) {
        final int id = count++;
        Point<T, R> modulateNode = new Point<T, R>(prevPoint) {
            @Override
            public Vibration<T> vibrate(Vibration<R> transmitted) {
                return new Vibration<T>() {
                    @Override
                    public void happen(T effect) {
                        System.out.println("Point[" + id + "]" + " starts vibrating with modulation -> " + effect);
                        R m = mapper.apply(effect);
                        transmitted.happen(m);
                        System.out.println("Point[" + id + "]" + " finished vibration");
                    }
                };
            }
        };
        return new Wave<>(modulateNode);
    }

    public void generate(int n) {
        Point medium = this.prevPoint;
        while (medium.prevPoint != null) {
            medium = medium.prevPoint;
        }

        Vibration vibration = new Vibration() {
            public void happen(Object effect) {
                System.out.println("Requester finally received the vibration -> " + effect);
            }
        };
        for (Point p = this.prevPoint; p.prevPoint != null; p = p.prevPoint) {
            vibration = p.vibrate(vibration);
        }
        vibration = medium.vibrate(vibration);

        Iterator vibrator = medium.getVibrator();
        System.out.println("Vibration has been triggered");
        while (vibrator.hasNext()) {
            vibration.happen(vibrator.next());
        }

        System.out.println("Vibration effect has been propagated through the medium " + medium);
    }

}
