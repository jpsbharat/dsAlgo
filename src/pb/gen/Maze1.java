package pb.gen;

import java.util.Arrays;
import java.util.Scanner;

public class Maze1 {
    static int n;
    static int[][] a;
    static int path;

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
        System.out.println(search(a));
    }

    public static final int search(int[][] data) {
        int[][] mymap = new int[data.length][];
        for (int i = 0; i < data.length; i++) {
            mymap[i] = Arrays.copyOf(data[i], data[i].length);
        }
        return search(mymap, 0, 0);
    }

    public static int search(int[][] a, int i, int j) {
        if (!exist(a, i, j) || a[i][j] == 1)
            return 0; // no path here.
        if (i == a.length - 1 && j == a[i].length - 1) {
            return 1; // 1 path here.
        }
        a[i][j] = 1; // mark that we have seen this spot here
        int paths = 0; // introduce a counter...
        // add the additional paths as we find them
        paths += search(a, i + 1, j);
        paths += search(a, i - 1, j);
        paths += search(a, i, j + 1);
        paths += search(a, i, j - 1);
        a[i][j] = 0;
        return paths; // return the number of paths available from this point.
    }

    public static boolean exist(int[][] a, int i, int j) {
        return i >= 0 && j >= 0 && i < a.length && j < a[i].length;
    }
}
