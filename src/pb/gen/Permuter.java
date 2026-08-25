package pb.gen;

/**
 * Utility class containing a permutation generation method.
 */
public class Permuter {

    public interface Processor {
        void process(String s);
    }

    /**
     * Writes all permutations of the characters in s to standard ouput,
     * separated by newlines.
     */
    public static void permute(String s, Processor p) {
        permute("", s, p);
    }

    /**
     * Helper method: attaches all permutations of leftOver onto prefix and
     * prints them all.
     */
    private static void permute(String prefix, String leftOver, Processor p) {
        if (leftOver.equals("")) {
            p.process(prefix);
        } else {
            for (int i = 0; i < leftOver.length(); i++) {
                permute(prefix + leftOver.charAt(i), leftOver.substring(0, i)
                        + leftOver.substring(i + 1), p);
            }
        }
    }

    /**
     * Invokes permute on the command line argument. TODO: What the heck is a
     * main() method doing in here? You better not turn in code like this!
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Needs an argument string to permute");
        } else {
            permute(args[0], new Processor() {
                public void process(String s) {
                    System.out.println(s);
                }
            });
        }
    }
}
