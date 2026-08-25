package pb.gen;

/**
 * An application that analyzes two algorithms for computing Binomial
 * Coefficients and displays a report to standard output. The first one is a
 * "fast" one which essentially fills out Pascal's triangle; the second
 * moronically applies the well-known recurrence relation. Each analysis
 * displays a table where each row is the value of n in a call of C(n,n/2)
 * followed by the result of C(n,n/2) followed by the ratio of the actual
 * running time to two candidate time complexity functions. (This is a standard
 * way to empirically determine a complexity class - just see if a column
 * remains pretty much constant.)
 */
public class BinomialCoefficientGenerator {

    private static class Slow {

        /**
         * Computes Choose(n,k) the slow way by directly implementing the
         * recurrence relation.
         */
        public static int C(int n, int k) {
            if (k < 0 || k > n) {
                throw new IllegalArgumentException("Can't do C(" + n + "," + k
                        + ")");
            } else if (k == 0 || n == k) {
                return 1;
            } else {
                return C(n - 1, k) + C(n - 1, k - 1);
            }
        }

        /**
         * Times various runs of C(n,k) looking for whether n^2 or 2^n is the
         * best empirical estimate of the complexity.
         */
        public static void analyze() {
            System.out.println("\nAnalyzing Horrible Recursive Solution");
            System.out.println("Testing ratios for n^2 and 2^n");
            for (int n = 20; n < 30; n += 1) {
                long start = System.currentTimeMillis();
                int result = C(n, n / 2);
                long stop = System.currentTimeMillis();
                double duration = stop - start;
                System.out.printf("%5d%20d%20.14f%20.14f\n", n, result,
                        duration / (n * n), duration / Math.pow(2.0, n));
            }
        }
    }

    private static class Fast {

        /**
         * Computes Choose(n,k) by filling out Pascal's triangle.
         */
        public static int C(int n, int k) {
            if (k < 0 || k > n) {
                throw new IllegalArgumentException("Can't do C(" + n + "," + k
                        + ")");
            }
            int[][] cache = new int[n + 1][n + 1];
            for (int i = 0; i <= n; i++) {
                cache[i][0] = cache[i][i] = 1;
                for (int j = 1; j <= i - 1; j++) {
                    cache[i][j] = cache[i - 1][j] + cache[i - 1][j - 1];
                }
            }
            return cache[n][k];
        }

        /**
         * Times various runs of C(n,k) looking for whether nlogn or n^2 is the
         * best empirical estimate of the complexity.
         */
        public static void analyze() {
            System.out.println("\nAnalyzing Dynamic Programming Solution");
            System.out.println("Testing ratios for n*log(n) and n^2");
            System.out.println("Note that overflow is ignored");
            for (int n = 500; n < 1500; n += 100) {
                long start = System.currentTimeMillis();
                int result = C(n, n / 2);
                long stop = System.currentTimeMillis();
                double duration = stop - start;
                System.out.printf("%5d%20d%20.14f%20.14f\n", n, result,
                        duration / (n * Math.log(n)), duration / (n * n));
            }
        }
    }

    /**
     * Runs each version.
     */
    public static void main(String[] args) {
        Fast.analyze();
        Slow.analyze();
    }
}
