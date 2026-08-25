package pb.gen;

import java.util.Stack;

public class TrappingRainWater2D {

    public static void main(String[] a) {
        int[] h = new int[]{1, 2, 3, 4, 5, 9, 6, 4, 2, 3, 5, 8, 4, 7};
        System.out.println(trapUsingStack(h));
    }

    public static int trap2Pointer(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int result = 0;
        int leftMax = 0;
        int rightMax = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    result += (leftMax - height[left]);
                }
                ++left;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    result += (rightMax - height[right]);
                }
                --right;
            }
        }
        return result;
    }

    public static int trapUsingStack(int[] height) {
        int result = 0;
        int current = 0;
        Stack<Integer> st = new Stack<Integer>();
        while (current < height.length) {
            while (!st.empty() && height[current] > height[st.peek()]) {
                int top = st.pop();
                if (st.empty()) {
                    break;
                }
                int distance = current - st.peek() - 1;
                int boundedHeight = Math
                        .min(height[current], height[st.peek()]) - height[top];
                result += distance * boundedHeight;
            }
            st.push(current++);
        }
        return result;
    }

    public static int trapWater(int[] A) {
        if (A == null)
            return 0;
        int len = A.length;
        if (len < 3)
            return 0;
        Stack<Integer> stack = new Stack<Integer>();
        int sum = 0;
        for (int i = 0; i < len; i++) {
            if (!stack.isEmpty() && A[stack.peek()] <= A[i]) {
                int base = A[stack.pop()];
                while (!stack.isEmpty() && A[stack.peek()] <= A[i]) {
                    int area = (A[stack.peek()] - base)
                            * (i - stack.peek() - 1);
                    sum += area;
                    base = A[stack.pop()];
                }
                sum += stack.isEmpty() ? 0 : (A[i] - base)
                                             * (i - stack.peek() - 1);
            }
            stack.push(i);
        }
        return sum;
    }

    public static int trapDP(int[] height) {
        if (height == null)
            return 0;

        int result = 0;
        int size = height.length;
        int[] leftMax = new int[size];
        int[] rightMax = new int[size];

        leftMax[0] = height[0];
        for (int i = 1; i < size; i++) {
            leftMax[i] = Math.max(height[i], leftMax[i - 1]);
        }

        rightMax[size - 1] = height[size - 1];
        for (int i = size - 2; i >= 0; i--) {
            rightMax[i] = Math.max(height[i], rightMax[i + 1]);
        }

        for (int i = 1; i < size - 1; i++) {
            result += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return result;
    }

    public static int trapBruteForce(int[] height) {
        int result = 0;
        int size = height.length;
        for (int i = 1; i < size - 1; i++) {
            int maxLeft = 0;
            int maxRight = 0;
            for (int j = i; j >= 0; j--) { // Search the left part for max bar
                // size
                maxLeft = Math.max(maxLeft, height[j]);
            }

            for (int j = i; j < size; j++) { // Search the right part for max
                // bar size
                maxRight = Math.max(maxRight, height[j]);
            }
            result += Math.min(maxLeft, maxRight) - height[i];
        }
        return result;
    }
}
