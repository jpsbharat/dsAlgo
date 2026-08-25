package pb.sw;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
}
