package pb;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CommonUtils {

    public static void main(String[] args) {
        System.out.println(validateBracket("((()))()()"));
        System.out.println(validateBracket("))((())"));
        System.out.println(validateBracket("(((((((((((("));
        // ( ( ( )
        //1. [(]
        //2. [((]
        //3. [(((]
        //4. [(((] -> [((]
        //
        //
        System.out.println(longestValidParenthesis("))((())"));
        System.out.println(longestValidParenthesis("())"));
        System.out.println(longestValidParenthesis("(()())"));
        System.out.println(longestValidParenthesis("(()()(()"));
    }

    public static boolean validateBracket(String input) {
        boolean valid = true;
        Map<Character, Character> m = new HashMap<>();
        m.put('(', ')');
        Stack<Character> s = new Stack<>();
        for (Character curr : input.toCharArray()) {
            if (m.containsKey(curr)) {
                s.push(curr);
            } else {
                if (s.isEmpty()) {
                    valid = false;
                } else {
                    Character prev = s.peek();
                    if (m.get(prev).equals(curr)) {
                        s.pop();
                    } else {
                        valid = false;
                    }
                }
            }
        }

        if (!s.isEmpty()) {
            valid = false;
        }
        return valid;
    }

    public static int longestValidParenthesis(String input) {
        Map<Character, Character> m = new HashMap<>();
        m.put('(', ')');
        Stack<Integer> s = new Stack<>();
        s.push(-1);
        int index = 0;
        int max = 0;
        for (Character curr : input.toCharArray()) {
            if (m.containsKey(curr)) {
                s.push(index);
            } else {
                s.pop();
                if (s.isEmpty()) {
                    s.push(index);
                } else {
                    max = Math.max(max, index - s.peek());
                }
            }
            index++;
        }
        return max;
    }
}
