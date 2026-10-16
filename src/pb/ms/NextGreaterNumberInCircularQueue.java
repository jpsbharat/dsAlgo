package pb.ms;

import java.util.Arrays;
import java.util.Stack;

public class NextGreaterNumberInCircularQueue {

    public static void test(int[] nums) {
        System.out.println("Next Greater Elements: " + Arrays.toString(nextGreaterElements(nums)));
        System.out.println("Next Greater Elements using MS: " + Arrays.toString(nextGreaterElementsMS(nums)));
    }

    public static void main(String[] args) {
        int[] nums = {2, 1, 5, 6, 2, 3};
        test(nums);
        nums = new int[]{2, 8, 5, 6, 2, 3};
        test(nums);
    }

    public static int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = -1; // Initialize with -1
            for (int j = 1; j < n; j++) {
                int nextIndex = (i + j) % n;
                if (nums[nextIndex] > nums[i]) {
                    result[i] = nums[nextIndex];
                    break;
                }
            }
        }
        return result;
    }

    public static int[] nextGreaterElementsMS(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();
        Arrays.fill(result, -1);

        for (int i = 0; i < 2 * n; i++) {
            int j = i % n;
            while (!stack.isEmpty() && nums[stack.peek()] < nums[j]) {
                result[stack.pop()] = nums[j];
            }
            stack.push(j);
        }
        return result;
    }
}
