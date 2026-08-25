package ds.stack;

import java.util.Stack;

public class LargetsAreaInHistogram {

    public static void main(String[] args) {
        int[] height = new int[]{2, 8, 5, 6, 2, 3}; //8,6,15,3,10,12
        System.out.println(largestArea(height));
        height = new int[]{3, 8, 5, 6, 4, 4}; //8,6,15,4,20,18
        // s -> 3,8 c -> 5
        // s -> 3 c -> 5 => 8
        // s -> 3,5 c -> 6
        // s -> 3,5,6 c -> 4
        // s -> 3,5 c -> 4 => 6
        // s -> 3 c -> 4 => 15
        // s -> 3,4 c -> 4
        // s -> 3,4,4 c -> 0
        // s -> 3,4 c -> 0 => 4
        // s -> 3 c -> 0 => 20
        // s -> {} c -> 0 => 18
        System.out.println(largestArea(height));
        height = new int[]{3, 8, 9, 10, 4, 5};
        System.out.println(largestArea(height));
    }

    public static int largestArea(int[] a) {
        int result = 0;
        Stack<Integer> s = new Stack<>();
        for (int i = 0; i <= a.length; i++) {
            int currH = (i == a.length) ? 0 : a[i];
            while (!s.isEmpty() && currH < a[s.peek()]) {
                int top = s.pop();
                int w = i;
                int f = -1;
                if (!s.isEmpty()) {
                    f = s.peek();
                    w = i - s.peek() - 1;
                }
                int area = a[top] * w;
                System.out.println("area(" + f + ", " + i + ", " + w + ") = " + area);
                result = Math.max(result, area);
            }
            s.push(i);
        }
        System.out.println(s);
        return result;
    }
}
