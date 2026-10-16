package pb.comb;

public class PowerSet {

    public static void main(String[] args) {
        String[] set = {"a", "b", "c"};
        generatePowerSet(set);
    }

    public static <T> void generatePowerSet(T[] set) {
        int n = set.length;
        int powerSetSize = (int) Math.pow(2, n);

        for (int i = 0; i < powerSetSize; i++) {
            System.out.print("{ ");
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    System.out.print(set[j] + " ");
                }
            }
            System.out.println("}");
        }
    }
}
