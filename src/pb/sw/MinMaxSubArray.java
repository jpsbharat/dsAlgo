package pb.sw;

import java.util.*;

public class MinMaxSubArray {

    public static int maxSumSubArrayWithFixedSize(int[] a, int k) {
        int result = 0;
        int ksum = 0;
        for (int i = 0; i < k; i++) {
            ksum += a[i];
        }
        result = ksum;
        for (int i = k; i < a.length - k - 1; i++) {
            ksum = ksum - a[i - 1] + a[i];
            result = Math.max(result, ksum);
        }
        return result;
    }

    public static int minLengthSubArrayWithGivenSum(int[] a, int k) {
        int result = Integer.MAX_VALUE;
        int sum = 0;
        int l = 0;
        for (int r = 0; r < a.length - 1; r++) {
            sum += a[r];
            while (sum >= k) {
                result = Math.min(result, r - l + 1);
                sum -= a[l];
                l++;
            }
        }
        return result == Integer.MAX_VALUE ? 0 : result;
    }

    public static int findLongestSubstringWithExactlyKDistinctCharacters(String a, int k) {
        int result = -1;
        int l = 0;
        Map<Character, Integer> fmap = new HashMap<>();
        for (int r = 0; r < a.length() - 1; r++) {
            Character c = a.charAt(r);
            fmap.put(c, fmap.getOrDefault(c, 0) + 1);
            while (fmap.size() > k) {
                Character lc = a.charAt(l);
                fmap.put(lc, fmap.get(lc) - 1);
                if (fmap.get(lc) == 0) {
                    fmap.remove(lc);
                }
                l++;
            }
            if (fmap.size() == k) {
                result = Math.max(result, r - l + 1);
            }
        }
        return result;
    }

    public static int findLongestSubstringWithAtMostKDistinctCharacters(String a, int k) {
        int result = -1;
        int l = 0;
        Map<Character, Integer> fmap = new HashMap<>();
        for (int r = 0; r < a.length() - 1; r++) {
            Character c = a.charAt(r);
            fmap.put(c, fmap.getOrDefault(c, 0) + 1);
            while (fmap.size() > k) {
                Character lc = a.charAt(l);
                fmap.put(lc, fmap.get(lc) - 1);
                if (fmap.get(lc) == 0) {
                    fmap.remove(lc);
                }
                l++;
            }
            result = Math.max(result, r - l + 1);
        }
        return result;
    }

    public static int findLongestSubstringWithAtLeastKDistinctCharacters(String a, int k) {
        HashSet<Character> totalUniqueChars = new HashSet<>();
        for (int i = 0; i < a.length(); i++) {
            totalUniqueChars.add(a.charAt(i));
        }

        if (totalUniqueChars.size() < k) {
            return -1;
        }
        return a.length();
    }

    public static int findLongestSubstringWithRepeatingCharacters(String a) {
        int result = 0;
        int currLength = 1;
        for (int i = 1; i < a.length(); i++) {
            if (a.charAt(i) == a.charAt(i - 1)) {
                currLength++;
            } else {
                result = Math.max(currLength, result);
                currLength = 1;
            }
        }
        return result;
    }

    public static int findLongestSubstringWithoutRepeatingCharacters(String a) {
        int result = 0;
        int l = 0;
        Map<Character, Integer> imap = new HashMap<>();
        for (int r = 0; r < a.length(); r++) {
            Character c = a.charAt(r);
            if (imap.containsKey(c) && imap.get(c) >= l) {
                l = imap.get(c) + 1;
            }
            imap.put(c, r);
            result = Math.max(result, r - l + 1);
        }
        return result;
    }

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        // Map to keep track of the count of characters in the current window
        Map<Character, Integer> windowCounts = new HashMap<>();

        int maxLength = 0;
        int left = 0; // Left boundary of the window

        // Expand the right boundary of the window
        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            windowCounts.put(rightChar, windowCounts.getOrDefault(rightChar, 0) + 1);

            // While the current character is a duplicate, shrink the window from the left
            while (windowCounts.get(rightChar) > 1) {
                char leftChar = s.charAt(left);
                windowCounts.put(leftChar, windowCounts.get(leftChar) - 1);
                left++; // Move the left boundary forward
            }

            // Calculate the maximum window size seen so far
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s == null || p == null || s.length() < p.length()) {
            return result;
        }

        // 1. Build the frequency map for the target string p
        Map<Character, Integer> targetCounts = new HashMap<>();
        for (char c : p.toCharArray()) {
            targetCounts.put(c, targetCounts.getOrDefault(c, 0) + 1);
        }

        // Map to track the frequencies in our active sliding window
        Map<Character, Integer> windowCounts = new HashMap<>();
        int left = 0;
        int fixedSize = p.length();

        // 2. Slide the fixed window across string s
        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            windowCounts.put(rightChar, windowCounts.getOrDefault(rightChar, 0) + 1);

            // If the window size exceeds the fixed limit, remove the leftmost character
            if (right - left + 1 > fixedSize) {
                char leftChar = s.charAt(left);
                windowCounts.put(leftChar, windowCounts.get(leftChar) - 1);

                // Clean up the map key if the frequency hits 0 to keep maps clean
                if (windowCounts.get(leftChar) == 0) {
                    windowCounts.remove(leftChar);
                }
                left++; // Slide the left boundary forward by exactly 1 position
            }

            // 3. When the window hits the exact target size, compare maps directly
            if (right - left + 1 == fixedSize) {
                if (windowCounts.equals(targetCounts)) {
                    result.add(left);
                }
            }
        }

        return result;
    }
}