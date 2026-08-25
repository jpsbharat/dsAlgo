package pb.gen;

import java.util.Scanner;

/*
 0 0 1 0 0 1 0

 1 0 1 1 0 0 0

 0 0 0 0 1 0 1

 1 0 1 0 0 0 0

 1 0 1 1 0 1 0

 1 0 0 0 0 1 0

 1 1 1 1 0 0 0
 */
public class Maze2 {
    static int n;
    static int[][] a;
    static int path;

    public static void main(String[] ar) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        a = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = sc.nextInt();
            }
        }
        sc.close();
        search(0, 0);
        System.out.println(path);
    }

    /*
     * 4 0 0 0 0 0 1 1 0 0 1 1 0 0 0 0 0
     *
     * 4 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0
     */
    public static void search(int i, int j) {
        if (!exist(i, j)) {
            System.out.println("Returned due to boundry for " + i + ", " + j);
            return;
        }

        if (a[i][j] == 1) {
            System.out.println("Returned due to wall for " + i + ", " + j);
            return;
        }

        System.out.println(i + ", " + j);
        if (i == n - 1 && j == n - 1) {
            path++;
            return;
        }
        a[i][j] = 1;
        search(i + 1, j);
        search(i - 1, j);
        search(i, j + 1);
        search(i, j - 1);
        a[i][j] = 0;
    }

    public static boolean exist(int i, int j) {
        return i >= 0 && j >= 0 && i < n && j < n;
    }
}
