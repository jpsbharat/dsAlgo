package ds.stack;

import java.util.Stack;

public class TrappingRainWater {

    public static void main(String[] args) {
        int[] height = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(waterTrappedMS(height));
        System.out.println(waterTrappedLRMax(height));
        System.out.println(waterTrapped2P(height));
        height = new int[]{2, 8, 5, 6, 2, 3};
        System.out.println(waterTrappedMS(height));
        System.out.println(waterTrappedLRMax(height));
        System.out.println(waterTrapped2P(height));
    }

    public static Integer waterTrappedMS(int[] a) {
        int result = 0;
        Stack<Integer> s = new Stack<>();
        for (int i = 0; i < a.length; i++) {
            while (!s.isEmpty() && a[s.peek()] < a[i]) {
                Integer top = s.pop();
                if (!s.isEmpty()) {
                    Integer curr = s.peek();
                    int h = Math.min(a[curr], a[i]) - a[top];
                    int w = i - curr - 1;
                    result += h * w;
                }
            }
            s.push(i);
        }
        return result;
    }

    public static Integer waterTrappedLRMax(int[] a) {
        int result = 0;
        int[] lmax = new int[a.length];
        lmax[0] = a[0];
        for (int i = 1; i < a.length - 1; i++) {
            lmax[i] = Math.max(a[i], lmax[i - 1]);
        }
        int[] rmax = new int[a.length];
        rmax[a.length - 1] = a[a.length - 1];
        for (int i = a.length - 2; i >= 0; i--) {
            rmax[i] = Math.max(a[i], rmax[i + 1]);
        }

        for (int i = 1; i < a.length - 1; i++) {
            result += Math.min(rmax[i], lmax[i]) - a[i];
        }
        return result;
    }

    public static Integer waterTrapped2P(int[] a) {
        int result = 0;
        int l = 0;
        int r = a.length - 1;
        int lmx = a[l];
        int rmx = a[r];
        while (l < r) {
            if (a[l] < a[r]) {
                if (a[l] >= lmx) {
                    lmx = a[l];
                } else {
                    result += lmx - a[l];
                }
                l++;
            } else {
                if (a[r] >= rmx) {
                    rmx = a[r];
                } else {
                    result += rmx - a[r];
                }
                r--;
            }
        }
        return result;
    }
}
