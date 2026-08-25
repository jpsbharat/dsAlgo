package pb.gen;

import java.util.ArrayList;
import java.util.List;

public class AllCombinations {
    private int[] B = null;

    public void combinations(int[] B) {
        this.B = B;
        int[] A = new int[B.length];
        combinations(A, 0);
        System.out.println();
    }

    private void combinations(int[] A, int x) {
        if (x == A.length - 1) {
            A[x] = 0; // last digit, don't select it
            printArray(A); // print the set
            A[x] = 1; // // last digit, select it
            printArray(A);
            return;
        }

        A[x] = 0; // either you will not select this digit
        combinations(A, x + 1);
        A[x] = 1; // either you will select this digit
        combinations(A, x + 1);
    }

    public void printArray(int[] A) {
        boolean isNULL = true;
        System.out.print("{");
        for (int i = 0; i < B.length; i++) {
            if (A[i] == 1) {
                System.out.print(B[i] + "");
                isNULL = false;
            }
        }
        if (isNULL == false) {
            System.out.print("}");
            System.out.print("  ");
        }

        if (isNULL) {
            System.out.print("Empty");
            System.out.print("} ");
        }
    }

    public static void main(String[] args) {
        AllCombinations a = new AllCombinations();
        int[] A = {1, 2, 3};
        a.combinations(A);
        int[] B = {1, 2, 3, 4};
        a.combinations(B);
        int[] C = {1, 2, 3, 4, 5};
        a.combinations(C);

        List<Integer> lst1 = new ArrayList<Integer>();
        lst1.add(1);
        lst1.add(2);
        lst1.add(3);
        List<Integer> lst2 = new ArrayList<Integer>();
        lst2.add(3);
        lst2.add(2);
        lst2.add(1);
        System.out.println(lst1.equals(lst2));
        List<Integer> lst3 = new ArrayList<Integer>();
        lst3.add(3);
        lst3.add(2);
        lst3.add(1);
        System.out.println(lst3.equals(lst2));
    }
}
