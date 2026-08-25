package pb.sw;

import java.util.HashMap;
import java.util.Map;

public class KOperation {

    public static int longestSubarrayWithKChanges(int[] nums, int k) {
        int left = 0;
        int maxLen = 0;
        int maxFreq = 0;
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int right = 0; right < nums.length; right++) {
            freqMap.put(nums[right], freqMap.getOrDefault(nums[right], 0) + 1);
            maxFreq = Math.max(maxFreq, freqMap.get(nums[right]));

            // Current window size minus maxFreq gives the needed operations
            int operationsNeeded = (right - left + 1) - maxFreq;

            // Shrink window if we need more than k operations
            if (operationsNeeded > k) {
                freqMap.put(nums[left], freqMap.get(nums[left]) - 1);
                left++;
            }
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    public static int longestSubarrayWithKFlips(int[] nums, int k) {
        int left = 0;
        int maxLen = 0;
        int zeroCount = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeroCount++;
            }
            // Shrink window if operations exceed k
            while (zeroCount > k) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
            }
            // Update max length of valid window
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        testLongestSubarrayWithKFlips();
        testLongestSubarrayWithKChanges();
    }

    public static void testLongestSubarrayWithKFlips() {
        int[] nums = {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0};
        int k = 2;
        System.out.println(longestSubarrayWithKFlips(nums, k)); // Output: 5
    }

    public static void testLongestSubarrayWithKChanges() {
        int[] nums = {1, 2, 1, 4, 1, 3};
        int k = 2;
        System.out.println(longestSubarrayWithKChanges(nums, k)); // Output: 5
    }
}
