package pb.gen;

import java.util.Arrays;
import java.util.Scanner;

public class Maze {
    private int n;
    private int[][] a;

    /**
     * Array is a square matrix, whose elements are 0 for paths and 1 for walls.
     */
    public Maze(int[][] array) {
        // Copy the array, assuming that it is a square matrix
        n = array.length;
        a = new int[n][];
        for (int i = n - 1; i >= 0; i--) {
            a[i] = Arrays.copyOf(array[i], n);
        }
    }

    /*
     * 4 0 0 0 0 0 1 1 0 0 1 1 0 0 0 0 0
     *
     * 4 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0
     */
    public int pathsFrom(int i, int j) {
        if (!isInBounds(i, j)) {
            System.out.println("Returned due to boundry for " + i + ", " + j);
            return 0;
        }

        if (a[i][j] == 1) {
            System.out.println("Returned due to wall for " + i + ", " + j);
            return 0;
        }

        System.out.println(i + ", " + j);

        if (i == n - 1 && j == n - 1) {
            return 1;
        }

        try {
            a[i][j] = 1;
            return pathsFrom(i + 1, j) + pathsFrom(i - 1, j)
                    + pathsFrom(i, j + 1) + pathsFrom(i, j - 1);
        } finally {
            // Restore the state of the maze before returning
            a[i][j] = 0;
        }
    }

    private boolean isInBounds(int i, int j) {
        return i >= 0 && j >= 0 && i < n && j < n;
    }

    public static void main(String[] ar) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = sc.nextInt();
            }
        }
        sc.close();
        Maze m = new Maze(a);
        System.out.println(m.pathsFrom(0, 0));
    }

}
/*
 * In the implementation of pathsFrom() above, I've used a slick language trick
 * to sneak in a statement before returning. That code is equivalent to
 *
 * a[i][j] = 1; int paths = pathsFrom(i+1,j) + pathsFrom(i-1,j) +
 * pathsFrom(i,j+1) + pathsFrom(i,j-1); a[i][j] = 0; return paths;
 */
