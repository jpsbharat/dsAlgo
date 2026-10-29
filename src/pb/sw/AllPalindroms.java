package pb.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AllPalindroms {

    public static void main(String[] args) {
        String s = "ababa";
        List<String> allSubStringsSlidingWindow = findAllSubStringsSlidingWindow(s);
        System.out.println("All Substrings (Sliding Window): " + allSubStringsSlidingWindow);

        List<String> allSubStringsSlidingWindow_1 = findAllSubStringsSlidingWindow_1(s);
        System.out.println("All Substrings (Sliding Window 1): " + allSubStringsSlidingWindow_1);

        Set<String> palindromesSlidingWindow = findAllPalindromesSlidingWindow(s);
        System.out.println("Palindromes (Sliding Window): " + palindromesSlidingWindow);

        Set<String> palindromesSlidingWindow_1 = findAllPalindromesSlidingWindow_1(s);
        System.out.println("Palindromes (Sliding Window 1): " + palindromesSlidingWindow_1);

        Set<String> palindromesBruteForce = findAllPalindromesBruteForce(s);
        System.out.println("Palindromes (Brute Force): " + palindromesBruteForce);
    }

    public static List<String> findAllSubStringsSlidingWindow(String s) {
        int n = s.length();
        List<String> palindromes = new ArrayList<>();
        // Implementation for finding all palindromes would go here
        for(int i = 0; i < n; i++) {
            int l = i;
            int r = i;
            while(l >= 0 && r < n) {
                palindromes.add(s.substring(l, r + 1));
                l--;
                r++;
            }

            l = i;
            r = i + 1;
            while(l >= 0 && r < n) {
                palindromes.add(s.substring(l, r + 1));
                l--;
                r++;
            }
        }
        return palindromes;
    }

    public static List<String> findAllSubStringsSlidingWindow_1(String s) {
        int n = s.length();
        List<String> palindromes = new ArrayList<>();
        // Implementation for finding all palindromes would go here
        for(int i = 0; i < 2*n -1; i++) {
            int l = i / 2;
            int r = l + i % 2;
            while(l >= 0 && r < n) {
                palindromes.add(s.substring(l, r + 1));
                l--;
                r++;
            }
        }
        return palindromes;
    }

    public static Set<String> findAllPalindromesSlidingWindow(String s) {
        int n = s.length();
        Set<String> palindromes = new java.util.HashSet<>();
        // Implementation for finding all palindromes would go here
        for(int i = 0; i < n; i++) {
            int l = i;
            int r = i;
            while(l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
                palindromes.add(s.substring(l, r + 1));
                l--;
                r++;
            }

            l = i;
            r = i + 1;
            while(l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
                palindromes.add(s.substring(l, r + 1));
                l--;
                r++;
            }
        }
        return palindromes;
    }

    public static Set<String> findAllPalindromesSlidingWindow_1(String s) {
        int n = s.length();
        Set<String> palindromes = new java.util.HashSet<>();
        // Implementation for finding all palindromes would go here
        for(int i = 0; i < 2*n -1; i++) {
            int l = i / 2;
            int r = l + i % 2;
            while(l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
                palindromes.add(s.substring(l, r + 1));
                l--;
                r++;
            }
        }
        return palindromes;
    }

    public static Set<String> findAllPalindromesBruteForce(String s) {
        int n = s.length();
        Set<String> palindromes = new java.util.HashSet<>();
        // Implementation for finding all palindromes would go here
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j <= n; j++) {
                String substring = s.substring(i, j);
                if(isPalindrome(substring)) {
                    palindromes.add(substring);
                }
            }

        }
        return palindromes;
    }

    private static boolean isPalindrome(String substring) {
        String cleaned = substring.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        int left = 0;
        int right = cleaned.length() - 1;
        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
