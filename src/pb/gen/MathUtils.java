package pb.gen;

import java.util.*;

public class MathUtils {
    public static void main(String[] args) {
        List<Integer> inputList = new ArrayList<Integer>();
        inputList.add(1);
        inputList.add(2);
        inputList.add(3);
        inputList.add(4);
        System.out.println(getAllSubArray(inputList));
        System.out.println(getAllPairs(inputList));
        System.out.println(getAllPermutations(inputList));
        // System.out.println(evaluateInfix("(10 + 2) * 6"));
        System.out.println(calculate("10 + 2 * 6"));
        System.out.println(evaluateInfix("(10 + 2) * 6"));
        System.out.println(evaluateInfix("(10 + 2) * 6 - 9 * 65/(11-6)"));
        System.out.println(calculate("(10 + 2) * 6 - 9 * 65/(11-6)"));
        System.out.println(calculate("10 + 2 * 6"));
        System.out.println(calculate("(10 + 2) * 6"));
        System.out.println(evaluate("(10 + 2) * 6"));
        System.out.println(evaluate("10 + 2 * 6"));
        System.out.println(evaluate("100 * 2 + 12"));
        System.out.println(evaluate("100 * ( 2 + 12 )"));
        System.out.println(evaluate("100 * ( 2 + 12 ) / 14"));
        System.out.println(evaluateInfix("10 + 2 * 6"));
    }

    public static <T> List<Pair<T, T>> getAllPairs(List<T> inputList) {
        List<Pair<T, T>> result = new ArrayList<Pair<T, T>>();
        for (int i = 0; i < inputList.size() - 1; i++) {
            for (int j = i + 1; j < inputList.size(); j++) {
                result.add(new Pair<T, T>(inputList.get(i), inputList.get(j)));
            }
        }
        return result;
    }

    /*  Let�s observe the behavior for array = {1,2,3,4}
        All sub arrays are:
        [1] , [1 2], [1 2 3], [1 2 3 4],
        [2], [2 3], [2 3 4]
        [3], [3 4]
        [4]

        No of occurrences for each element
        1 appears 4 times
        2 appears 6 times
        3 appears 6 times
        4 appears 4 times

        For each element at first place � If we observe closely, element at first position, the sub arrays are
        For 1 = [1] , [1 2], [1 2 3], [1 2 3 4] and for 2 = [2], [2 3], [2 3 4], for 3 = [3], [3 4] so for element 1, no of occurrence at first position will be equal to n (n=4) here.
        Next element which is �2� the number of occurrence at the first position will be one less than n. means n � 1, and so on
        So for ith element in array will have appearances at the first position in all the sub arrays will be = (n-i).
        So for the first position, occurrences are
        1 appears 4 times.
        2 appears 3 times.
        3 appears 2 times.
        4 appears 1 times.

        From Step 1 if we subtract the number of occurrences in above step, the remaining occurrences are (i is the iteration index)
        1 = 0, n = 4, i = 0
        2 = 3, n = 4,  i = 1
        3 = 4, n = 4,  i = 2
        4 = 3, n = 4, i = 3
        From the step above, the formula which will give this result will be = (n-i)*i
        So Total number of occurrences for ith index element in array will be = (n-i) + (n-i)*I => (n-i)*(i+1)
        So for array {1,2,3,4}
        1*(4-0)*(0+1) +
        2*(4-1)*(1+1) +
        3*(4-2)*(2+1) +
        4*(4-3)*(3+1) = 1*4 + 2*6 + 3*6 + 4*4 = 50
        In general,
        For ith index the number of occurrences are = (n-i)*(i+1)
        Contribution of each element in totalSum is arrA[i]*(n-i)*(i+1)
    */
    public int getAllSubArraySum(List<Integer> inputList) {
        int n = inputList.size();
        int totalSum = 0;
        for (int i = 0; i < n; i++) {
            totalSum += inputList.get(i) * (n - i) * (i + 1);
        }
        return totalSum;
    }

    public static <T> List<List<T>> getAllSubArray(List<T> inputList) {
        List<List<T>> result = new ArrayList<List<T>>();
        for (int i = 0; i < inputList.size(); i++) {
            for (int j = i + 1; j <= inputList.size(); j++) {
                List<T> tmp = new ArrayList<T>();
                for (int k = i; k < j; k++) {
                    tmp.add(inputList.get(k));
                }
                result.add(tmp);
            }
        }
        return result;
    }

    public static <T> Set<List<T>> getAllPermutations(List<T> inputList) {
        Set<List<T>> subsetsOfInputList = new LinkedHashSet<List<T>>();
        getAllPermutations0(subsetsOfInputList, inputList, 0, inputList.size());
        return subsetsOfInputList;
    }

    private static <T> void getAllPermutations0(
            Set<List<T>> subsetsOfInputList, List<T> inputList, int index,
            int size) {
        int x;
        if (index == size) {
            List<T> outputList = new ArrayList<>();
            for (int i = 0; i < inputList.size(); i++) {
                outputList.add(inputList.get(i));
            }
            subsetsOfInputList.add(outputList);
        } else {
            for (x = index; x < size; x++) {
                T temp = inputList.get(index);
                inputList.set(index, inputList.get(x));
                inputList.set(x, temp);
                getAllPermutations0(subsetsOfInputList, inputList, index + 1,
                        size);
                temp = inputList.get(index);
                inputList.set(index, inputList.get(x));
                inputList.set(x, temp);
            }
        }
    }

    public static <T> Set<List<T>> getPowerSet(List<T> inputList) {
        Set<List<T>> subsetsOfInputList = new LinkedHashSet<List<T>>();
        List<T> outputList = new ArrayList<>();
        getPowerSet0(subsetsOfInputList, inputList, outputList, 0);
        subsetsOfInputList.add(outputList);

        return subsetsOfInputList;
    }

    private static <T> void getPowerSet0(Set<List<T>> subsetsOfInputList,
                                         List<T> inputList, List<T> outputList, int index) {
        subsetsOfInputList.add(new ArrayList<T>(outputList));
        for (int i = index; i < inputList.size(); i++) {
            System.out.println("Added: " + inputList.get(i) + " at Index: "
                    + index + " and i= " + i);
            outputList.add(inputList.get(i));
            getPowerSet0(subsetsOfInputList, inputList, outputList, i + 1);
            System.out.println("Removed: "
                    + outputList.get(outputList.size() - 1) + " at Index: "
                    + index + " and i= " + i);
            outputList.remove(outputList.size() - 1);
        }

        System.out.println("Returned at Index: " + index);
    }

    public static List<List<Integer>> getFactors(int n) {
        List<List<Integer>> result = new ArrayList<>();

        if (n <= 1) {
            return result;
        }

        List<Integer> curr = new ArrayList<>();

        getFactors0(2, 1, n, curr, result);

        return result;
    }

    private static void getFactors0(int start, int product, int n,
                                    List<Integer> curr, List<List<Integer>> result) {
        if (start > n || product > n) {
            return;
        }

        if (product == n) {
            result.add(new ArrayList<Integer>(curr));
            return;
        }

        for (int i = start; i < n; i++) {
            if (i * product > n) {
                break;
            }

            if (n % (product * i) == 0) {
                curr.add(i);
                getFactors0(i, product * i, n, curr, result);
                curr.remove(curr.size() - 1);
            }
        }
    }

    public static int calculate(String s) {
        if (validateBrackets(s)) {
            return evaluatePostfix(infixToPostfix(s));
        }
        return Integer.MIN_VALUE;
    }

    public static boolean validateBrackets(String brackets) {
        boolean result = true;
        Stack<Character> stack = new Stack<Character>();
        char current, previous;
        for (int i = 0; i < brackets.length(); i++) {
            current = brackets.charAt(i);
            if (current == '(' || current == '[' || current == '{') {
                stack.push(current);
            } else if (current == ')' || current == ']' || current == '}') {
                if (stack.isEmpty()) {
                    result = false;
                } else {
                    previous = stack.peek();
                    if ((current == ')' && previous == '(')
                            || (current == ']' && previous == '[')
                            || (current == '}' && previous == '{')) {
                        stack.pop();
                    } else {
                        result = false;
                    }
                }
            }
        }
        if (!stack.isEmpty()) {
            result = false;
        }
        return result;
    }

    public static int evaluatePostfix(List<Object> postfix) {
        Stack<Integer> operands = new Stack<Integer>();
        int a = 0, b = 0;
        for (Object s : postfix) {
            if (s instanceof Character) {
                char c = (Character) s;
                b = operands.pop();
                a = operands.pop();
                switch (c) {
                    case '+':
                        operands.push(a + b);
                        break;
                    case '-':
                        operands.push(a - b);
                        break;
                    case '*':
                        operands.push(a * b);
                        break;
                    default:
                        operands.push(a / b);
                }
            } else { // instanceof Integer
                operands.push((Integer) s);
            }
        }
        return operands.pop();
    }

    public static List<Object> infixToPostfix(String s) {
        Stack<Character> operators = new Stack<Character>();
        List<Object> postfix = new LinkedList<Object>();

        int numberBuffer = 0;
        boolean bufferingOperand = false;
        for (char c : s.toCharArray()) {
            if (c >= '0' && c <= '9') {
                numberBuffer = numberBuffer * 10 + c - '0';
                bufferingOperand = true;
            } else {
                if (bufferingOperand)
                    postfix.add(numberBuffer);
                numberBuffer = 0;
                bufferingOperand = false;

                if (c == ' ' || c == '\t')
                    continue;

                if (c == '(') {
                    operators.push('(');
                } else if (c == ')') {
                    while (operators.peek() != '(')
                        postfix.add(operators.pop());
                    operators.pop(); // popping "("
                } else { // operator
                    while (!operators.isEmpty()
                            && rank(c) <= rank(operators.peek()))
                        postfix.add(operators.pop());
                    operators.push(c);
                }
            }
        }

        if (bufferingOperand)
            postfix.add(numberBuffer);

        while (!operators.isEmpty())
            postfix.add(operators.pop());

        return postfix;
    }

    public static int rank(char op) {
        // the bigger the number, the higher the rank
        switch (op) {
            case '+':
                return 1;
            case '-':
                return 1;
            case '*':
                return 2;
            case '/':
                return 2;
            default:
                return 0; // '('
        }
    }

    public static String evaluateInfix(String exps) {
        /** remove if any spaces from the expression **/
        exps = exps.replaceAll("\\s+", "");
        /** we assume that the expression is in valid format **/
        Stack<String> stack = new Stack<String>();
        /** break the expression into tokens **/
        StringTokenizer tokens = new StringTokenizer(exps, "{}()*/+-", true);
        while (tokens.hasMoreTokens()) {
            String tkn = tokens.nextToken();
            /** read each token and take action **/
            if (tkn.equals("(") || tkn.equals("{") || tkn.matches("[0-9]+")
                    || tkn.equals("*") || tkn.equals("/") || tkn.equals("+")
                    || tkn.equals("-")) {
                /** push token to the stack **/
                stack.push(tkn);
            } else if (tkn.equals("}") || tkn.equals(")")) {
                try {
                    int op2 = Integer.parseInt(stack.pop());
                    String oprnd = stack.pop();
                    int op1 = Integer.parseInt(stack.pop());
                    /** Below pop removes either } or ) from stack **/
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                    int result = 0;
                    if (oprnd.equals("*")) {
                        result = op1 * op2;
                    } else if (oprnd.equals("/")) {
                        result = op1 / op2;
                    } else if (oprnd.equals("+")) {
                        result = op1 + op2;
                    } else if (oprnd.equals("-")) {
                        result = op1 - op2;
                    }
                    /** push the result to the stack **/
                    stack.push(result + "");
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        String finalResult = "";
        try {
            finalResult = stack.pop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalResult;
    }

    public static int evaluate(String expression) {
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Integer> values = new Stack<Integer>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++) {
            // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            // Current token is a number, push it to stack for numbers
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number
                while (i < tokens.length && tokens[i] >= '0'
                        && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.toString()));
            }

            // Current token is an opening brace, push it to 'ops'
            else if (tokens[i] == '(')
                ops.push(tokens[i]);

                // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')') {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }

            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*'
                    || tokens[i] == '/') {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.isEmpty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        // Top of 'values' contains result, return it
        return values.pop();
    }

    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public static int applyOp(char op, int b, int a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

    public static int evaluatePrefix(List<Object> preFix) {
        Stack<Integer> operands = new Stack<Integer>();
        int a = 0, b = 0;
        for (Object s : preFix) {
            if (s instanceof Integer) { // instanceof Integer
                operands.push((Integer) s);
            } else {
                char c = (Character) s;
                b = operands.pop();
                a = operands.pop();
                switch (c) {
                    case '+':
                        operands.push(a + b);
                        break;
                    case '-':
                        operands.push(a - b);
                        break;
                    case '*':
                        operands.push(a * b);
                        break;
                    default:
                        operands.push(a / b);
                }
            }
        }
        return operands.pop();
    }

    public static class Pair<F, S> {
        private F first;
        private S second;

        public Pair(F first, S second) {
            super();
            this.first = first;
            this.second = second;
        }

        public F getFirst() {
            return first;
        }

        public void setFirst(F first) {
            this.first = first;
        }

        public S getSecond() {
            return second;
        }

        public void setSecond(S second) {
            this.second = second;
        }

        @Override
        public String toString() {
            return "Pair [first=" + first + ", second=" + second + "]";
        }
    }
}
