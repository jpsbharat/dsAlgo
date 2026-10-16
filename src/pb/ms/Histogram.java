package pb.ms;

import java.util.Stack;

public class Histogram {

    public static void test(int[] heights) {
        System.out.println("Largest Rectangle Area: " + largestRectangleArea(heights));
        System.out.println("Largest Rectangle Area using MS: " + largestRectangleAreaMS(heights));
    }

    public static void main(String[] args) {
        int[] heights = {2, 1, 5, 6, 2, 3};
        test(heights);
        heights = new int[]{2, 8, 5, 6, 2, 3};
        test(heights);
    }

    public static int largestRectangleAreaMS(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int height = heights[stack.pop()];
            int width = stack.isEmpty() ? n : n - stack.peek() - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        return maxArea;
    }

    public static int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        int[] left = new int[n];
        int[] right = new int[n];

        // Calculate left limits
        for (int i = 0; i < n; i++) {
            left[i] = i;
            while (left[i] > 0 && heights[left[i] - 1] >= heights[i]) {
                left[i] = left[left[i] - 1];
            }
        }

        // Calculate right limits
        for (int i = n - 1; i >= 0; i--) {
            right[i] = i;
            while (right[i] < n - 1 && heights[right[i] + 1] >= heights[i]) {
                right[i] = right[right[i] + 1];
            }
        }

        // Calculate max area
        for (int i = 0; i < n; i++) {
            int width = right[i] - left[i] + 1;
            int area = width * heights[i];
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }
}
