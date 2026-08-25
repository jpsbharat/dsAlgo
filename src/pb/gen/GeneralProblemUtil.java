package pb.gen;

import java.util.*;

public class GeneralProblemUtil {

    public static void main(String args[]) {
        findSortedArrayRotation(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        findSortedArrayRotation(new int[]{7, 8, 9, 10, 1, 2, 3, 4, 5, 6});
        testSubsetSumPowerSet();
        testMinimumCoinChangePowerSet();
        makeCoinChangeMem(new int[]{3, 7, 11}, 18);
        makeCoinChangeMem(new int[]{3, 2, 5, 7, 10}, 27);
        testPopulatePowerSetOptimized();
        char aChar = 'g';
        int val = (int) aChar;
        System.out.print(aChar + " = " + val);
        getSubsetSum();
        fib(8);
        testPopulatePowerSetOptimized();
        testLIS();
        // printSum();
        // printMax();
        // System.out.println(solution(5));
        // System.out.println(solution(15));
        int i;
        for (i = 0; i <= 10; ++i) {
            System.out.print(i + " ");
        }

        System.out.println(i);

        for (i = 0; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println(i);
        System.out.println(solution(1041));
        int nums2[] = {0, 0, -1, 0};
        maxContiguousSubArraySum(nums2);
    }

    public static int getMaxProductOfThreeNumbers(int[] numbers) {
        if (numbers == null || numbers.length < 3) {
            System.err.println("Invalid Array");
            System.exit(1);
        }

        Arrays.sort(numbers);
        int firstNum = Integer.MIN_VALUE;
        int secondNum = Integer.MIN_VALUE;
        int thirdNum = Integer.MIN_VALUE;
        int maxProduct = Integer.MIN_VALUE;
        for (int i = 0; i < numbers.length - 2; i++) {
            firstNum = numbers[i];
            secondNum = numbers[i + 1];
            thirdNum = numbers[i + 2];
            int tempProduct = Integer.MIN_VALUE;

            if (firstNum < 0 && secondNum < 0) {
                tempProduct = firstNum * secondNum
                        * numbers[numbers.length - 1];
                maxProduct = Math.max(maxProduct, tempProduct);
            }

            if (firstNum >= 0) {
                tempProduct = (numbers[numbers.length - 1]
                        * numbers[numbers.length - 2] * numbers[numbers.length - 3]);
                maxProduct = Math.max(maxProduct, tempProduct);
                break;
            } else {
                tempProduct = firstNum * secondNum * thirdNum;
                maxProduct = Math.max(maxProduct, tempProduct);
            }
        }
        return maxProduct;
    }

    public static void fib(int num) {
        if (num == 1 || num == 2) {
            System.out.println(1);
            return;
        }

        int prev1 = 1;
        int prev2 = 1;
        int result = 0;
        System.out.print(prev1 + " ");
        System.out.print(prev2 + " ");
        while (num >= 3) {
            result = prev2 + prev1;
            prev1 = prev2;
            prev2 = result;
            System.out.print(result + " ");
            num--;
        }

        // System.out.println(result);
    }

    public static void getSubsetSum() {
        int a[] = {0, 4, 2, 1, 3, 5, 11};
        int n = a.length - 1;
        int w = 12;

        System.out.println(Arrays.toString(a) + " and W = " + w);
        boolean[][] s = new boolean[n + 1][w + 1];
        for (int i = 0; i <= n; i++) {
            s[i][0] = true;
        }

        for (int j = 1; j <= w; j++) {
            s[0][j] = false;
        }

        for (int j = 1; j <= w; j++) {
            for (int i = 1; i <= n; i++) {
                if (j < a[i]) { // we cannot use A[i]
                    s[i][j] = s[i - 1][j];
                } else { // we can use A[i]
                    s[i][j] = s[i - 1][j - 1] || s[i - 1][j - a[i]];
                }
            }
        }

        // ---------------------------------------------

        if (s[n][w]) {
            System.out.println("We can represent W = " + w
                    + " by a subset sum of A.");
        } else {
            System.out.println("We cannot represent W = " + w
                    + " by a subset sum of A.");
        }

        for (int i = 1; i <= w; i++) {
            System.out.println(Arrays.toString(s[i - 1]));
        }

        // find the subset by stepping back
        System.out.println("Printing the subset:\n");
        if (s[n][w]) {
            int j = w;
            for (int i = n; i >= 1; i--) {
                if (!s[i - 1][j]) {
                    System.out.print(i + " = " + a[i] + ", ");
                    j -= a[i];
                }
            }
        }
    }

    // Returns true if there is a subset of set[] with sun equal to given sum
    public static boolean isSubsetSum(int set[], int n, int sum) {
        // The value of subset[i][j] will be true if there is a subset of
        // set[0..j-1]
        // with sum equal to i
        boolean[][] subset = new boolean[sum + 1][n + 1];

        // If sum is 0, then answer is true
        for (int i = 0; i <= n; i++)
            subset[0][i] = true;

        // If sum is not 0 and set is empty, then answer is false
        for (int i = 1; i <= sum; i++)
            subset[i][0] = false;

        // Fill the subset table in bottom up manner
        for (int i = 1; i <= sum; i++) {
            for (int j = 1; j <= n; j++) {
                subset[i][j] = subset[i][j - 1];
                if (i >= set[j - 1])
                    subset[i][j] = subset[i][j]
                            || subset[i - set[j - 1]][j - 1];
            }
        }

        int index1 = 0;
        int index2 = 0;
        for (int i = subset.length - 1; i > 0; i--) {
            boolean flag = false;
            for (int j = subset[i].length - 1; j > 0; j--) {
                if (subset[i][j]) {
                    index1 = i;
                    index2 = j;
                    flag = true;
                    break;
                }
            }

            if (flag) {
                break;
            }
        }

        while (index1 > 0 && index2 > 0) {
            if (!subset[index1 - 1][index2]) {
                System.out.println(set[index2 - 1] + " ");
                index1 = index1 - set[index2 - 1];
            }
            index2--;
        }

        /*
         * for (int i = 0; i <= sum; i++) { for (int j = 0; j <= n; j++) {
         * System.out.print(subset[i][j] + " "); } }
         */
        return subset[sum][n];
    }

    public static <T> void getSubsetSumPowerSet(List<T> inputList,
                                                int noOfElem, int targetSum) {
        Set<List<T>> subsetsOfInputList = new LinkedHashSet<List<T>>();
        List<T> outputList = new ArrayList<>();
        getSubsetSumPowerSet(subsetsOfInputList, inputList, outputList, 0,
                inputList.size(), targetSum);
        System.out.println("subsetsOfInputList.size = "
                + subsetsOfInputList.size());
        System.out.println("subsetsOfInputList = " + subsetsOfInputList);
    }

    private static <T> void getSubsetSumPowerSet(
            Set<List<T>> subsetsOfInputList, List<T> inputList,
            List<T> outputList, int curIndex, int noOfElem, int targetSum) {
        if (outputList.size() > noOfElem) {
            return;
        }

        if (outputList.size() == noOfElem) {
            int sum = 0;
            for (T t : outputList) {
                if (t instanceof Integer) {
                    sum += (Integer) t;
                }
            }

            if (sum == targetSum) {
                subsetsOfInputList.add(new ArrayList<T>(outputList));
            }
            return;
        }

        for (int i = curIndex; i < inputList.size(); i++) {
            outputList.add(inputList.get(i));
            getSubsetSumPowerSet(subsetsOfInputList, inputList, outputList, i + 1, noOfElem, targetSum);
            outputList.remove(outputList.size() - 1);
        }
    }

    public static int minimumCoinChange(int[] values, int sum) {
        int number = 0;
        int highest = values.length - 1;
        while (true) {
            if (values[highest] > sum) {
                highest--;
                if (highest < 0)
                    break;
            } else {
                if ((sum / values[highest]) >= 1) {
                    number += (sum / values[highest]);
                    sum = sum % values[highest];
                }
            }
        }

        return number;
    }

    public static int minimumNumber(int[] values, int sum) {
        int number = 0;
        int highest = values.length - 1;
        while (true) {
            if (values[highest] > sum) {
                highest--;
                if (highest < 0)
                    break;
            } else {
                if ((sum / values[highest]) >= 1) {
                    number += (sum / values[highest]);
                    sum = sum % values[highest];
                }
            }
        }

        return number;
    }

    public static int minimumNumberDP(int[] values, int sum) {
        int[] table = new int[sum + 1];
        for (int i = 0; i < table.length; ++i) {
            table[i] = Integer.MAX_VALUE;
        }

        table[0] = 0;

        for (int i = 1; i <= sum; ++i) {
            for (int j = 0; j < values.length; ++j) {
                if (values[j] <= i && (table[i - values[j]] + 1 < table[i])) {
                    table[i] = table[i - values[j]] + 1;
                }
            }
        }
        return table[sum];
    }

    public static int minCoin(int[] s, int sum) {
        int[] minCoins = new int[sum + 1];
        minCoins[0] = 0;

        for (int i = 1; i < minCoins.length; i++)
            minCoins[i] = Integer.MAX_VALUE;

        for (int i = 1; i <= sum; i++)
            for (int j = 0; j < s.length; j++)
                if ((s[j] <= i) && (minCoins[i - s[j]] + 1 < minCoins[i]))
                    minCoins[i] = minCoins[i - s[j]] + 1;

        return minCoins[sum];
    }

    public static <T> void minimumCoinChangePowerSet(List<T> inputList,
                                                     int targetSum) {
        int currSum = 0;
        Set<List<T>> subsetsOfInputList = new LinkedHashSet<List<T>>();
        List<T> outputList = new ArrayList<>();
        minimumCoinChangePowerSet(subsetsOfInputList, inputList, outputList, 0,
                currSum, targetSum);
        System.out.println("noOfWays = " + subsetsOfInputList.size());
        System.out.println("noOfWays Details = " + subsetsOfInputList);
    }

    private static <T> void minimumCoinChangePowerSet(
            Set<List<T>> subsetsOfInputList, List<T> inputList,
            List<T> outputList, int curIndex, int currSum, int targetSum) {
        if (targetSum < currSum) {
            return;
        }

        if (targetSum == currSum) {
            subsetsOfInputList.add(new ArrayList<T>(outputList));
            return;
        }

        for (int i = curIndex; i < inputList.size(); i++) {
            currSum += (Integer) inputList.get(i);
            outputList.add(inputList.get(i));
            minimumCoinChangePowerSet(subsetsOfInputList, inputList,
                    outputList, i + 1, currSum, targetSum);
            currSum -= (Integer) inputList.get(i);
            outputList.remove(outputList.size() - 1);
        }
    }

    public static <T> void makeCoinChangePowerSet(List<T> inputList,
                                                  int targetSum) {
        int currSum = 0;
        Set<List<T>> subsetsOfInputList = new LinkedHashSet<List<T>>();
        List<T> outputList = new ArrayList<>();
        makeCoinChangePowerSet(subsetsOfInputList, inputList, outputList, 0,
                currSum, targetSum);
        System.out.println("noOfWays = " + subsetsOfInputList.size());
        System.out.println("noOfWays Details = " + subsetsOfInputList);
    }

    private static <T> void makeCoinChangePowerSet(
            Set<List<T>> subsetsOfInputList, List<T> inputList,
            List<T> outputList, int curIndex, int currSum, int targetSum) {
        if (targetSum < currSum) {
            return;
        }

        if (targetSum == currSum) {
            subsetsOfInputList.add(new ArrayList<T>(outputList));
            return;
        }

        for (int i = curIndex; i < inputList.size(); i++) {
            currSum += (Integer) inputList.get(i);
            outputList.add(inputList.get(i));
            int moneySoFar = 0;
            while (moneySoFar <= targetSum) {
                int moneyRemaining = targetSum - moneySoFar;
                makeCoinChangePowerSet(subsetsOfInputList, inputList,
                        outputList, i + 1, currSum, moneyRemaining);
                moneySoFar += (Integer) inputList.get(curIndex);
            }
            currSum -= (Integer) inputList.get(i);
            outputList.remove(outputList.size() - 1);
        }
    }

    public static long makeCoinChangeMem(int[] coins, int money) {
        long result = makeCoinChangeMemHelper(coins, money, 0,
                new HashMap<String, Long>());
        System.out.println("result = " + result);
        return result;
    }

    public static long makeCoinChangeMemHelper(int[] coins, int money,
                                               int index, Map<String, Long> memo) {
        if (money == 0) {
            return 1;
        }

        if (index >= coins.length) {
            return 0;
        }

        String key = money + "#" + index;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        int moneySoFar = 0;
        long ways = 0;

        while (moneySoFar <= money) {
            int moneyRemaining = money - moneySoFar;
            ways += makeCoinChangeMemHelper(coins, moneyRemaining, index + 1,
                    memo);
            moneySoFar += coins[index];
        }

        memo.put(key, ways);
        return ways;
    }

    /*
     * recursive function to print all combinations of coins that sum to goal
     *
     * @param left - amount left that we need to sum to with remaining coins
     *
     * @param index - index of current coin denomination we are working with
     *
     * @param coins - array of coin values
     *
     * @param names - map of names for string formatting
     *
     * @param curr - current amounts of each type of coin we have
     */
    private static void findCoins(int left, int index, int[] coins,
                                  Map<Integer, String> names, Map<String, Integer> curr) {
        // not the last type of coin
        if (index < coins.length - 1) {
            // if we have not reached our goal value yet
            if (left > 0) {
                int coinAmount = coins[index];
                if (coinAmount <= left) {
                    // try all possible numbers of current coin given the amount
                    // that is left
                    for (int i = 0; i <= left / coinAmount; i++) {
                        curr.put(names.get(coinAmount), i);
                        findCoins(left - coinAmount * i, index + 1, coins,
                                names, curr);
                    }
                    // reset the current coin amount to zero before recursing
                    curr.put(names.get(coinAmount), 0);
                }
                // case when there is a coin whose value is greater than the
                // goal
                else {
                    findCoins(left, index + 1, coins, names, curr);
                }
            }
            // we've reached our goal, print out the current coin amounts
            else {
                printCurr(curr);
            }
        }
        // last type of coin
        else {
            // if we have not reached our goal value yet
            if (left > 0) {
                int coinAmount = coins[index];
                if (coinAmount <= left) {
                    // if the remainder of our goal is evenly divisble by our
                    // last
                    // coin value, we can make the goal amount
                    if (left % coinAmount == 0) {
                        // add last coin amount and print current values out
                        curr.put(names.get(coinAmount), left / coinAmount);
                        printCurr(curr);

                        // reset this coin amount to zero before recursing
                        curr.put(names.get(coinAmount), 0);
                    }
                }
            }
            // we've reached our goal, print out the current coin amounts
            else {
                printCurr(curr);
            }
        }
    }

    private static void printCurr(Map<String, Integer> curr) {
        Iterator<String> iter = curr.keySet().iterator();
        while (iter.hasNext()) {
            String denom = iter.next();
            System.out.print(curr.get(denom) + " " + denom + " ");
        }
        System.out.println();
    }

    public int trapWater(int[] A) {
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

    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        int n = m == 0 ? 0 : matrix[0].length;
        int[][] height = new int[m][n + 1];

        int maxArea = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '0') {
                    height[i][j] = 0;
                } else {
                    height[i][j] = i == 0 ? 1 : height[i - 1][j] + 1;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            int area = maxAreaInHist(height[i]);
            if (area > maxArea) {
                maxArea = area;
            }
        }

        return maxArea;
    }

    private int maxAreaInHist(int[] height) {
        Stack<Integer> stack = new Stack<Integer>();

        int i = 0;
        int max = 0;

        while (i < height.length) {
            if (stack.isEmpty() || height[stack.peek()] <= height[i]) {
                stack.push(i++);
            } else {
                int t = stack.pop();
                max = Math.max(max, height[t]
                        * (stack.isEmpty() ? i : i - stack.peek() - 1));
            }
        }

        return max;
    }

    public static int largestRectangleArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        Stack<Integer> stack = new Stack<Integer>();
        int max = 0;
        int i = 0;
        while (i < height.length) {
            // push index to stack when the current height is larger than the
            // previous one
            if (stack.isEmpty() || height[i] >= height[stack.peek()]) {
                stack.push(i);
                i++;
            } else {
                // calculate max value when the current height is less than the
                // previous one
                int p = stack.pop();
                int h = height[p];
                int w = stack.isEmpty() ? i : i - stack.peek() - 1;
                max = Math.max(h * w, max);
            }
        }

        while (!stack.isEmpty()) {
            int p = stack.pop();
            int h = height[p];
            int w = stack.isEmpty() ? i : i - stack.peek() - 1;
            max = Math.max(h * w, max);
        }

        return max;
    }

    /*
     * Given a 2D binary matrix filled with 0's and 1's, find the largest square
     * containing only 1's and return its area. 1 0 1 0 0 1 0 1 1 1 1 1 1 1 1 1
     * 0 0 1 0
     */
    public int maximalSquare(char[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
        int[][] dp = new int[rows + 1][cols + 1];
        int maxsqlen = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]),
                            dp[i - 1][j - 1]) + 1;
                    maxsqlen = Math.max(maxsqlen, dp[i][j]);
                }
            }
        }
        return maxsqlen * maxsqlen;
    }

    public int maximalSquareOpt(char[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
        int[] dp = new int[cols + 1];
        int maxsqlen = 0, prev = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                int temp = dp[j];
                if (matrix[i - 1][j - 1] == '1') {
                    dp[j] = Math.min(Math.min(dp[j - 1], prev), dp[j]) + 1;
                    maxsqlen = Math.max(maxsqlen, dp[j]);
                } else {
                    dp[j] = 0;
                }
                prev = temp;
            }
        }
        return maxsqlen * maxsqlen;
    }

    public static void spiralOrder(int[][] matrix) {
        if (matrix.length == 0) {
            return;
        }

        int top = 0;
        int down = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (true) {
            // Print top row
            for (int j = left; j <= right; ++j) {
                System.out.print(matrix[top][j] + " ");
            }
            top++;
            if (top > down || left > right) {
                break;
            }

            // Print the rightmost column
            for (int i = top; i <= down; ++i) {
                System.out.print(matrix[i][right] + " ");
            }
            right--;
            if (top > down || left > right) {
                break;
            }

            // Print the bottom row
            for (int j = right; j >= left; --j) {
                System.out.print(matrix[down][j] + " ");
            }
            down--;
            if (top > down || left > right) {
                break;
            }

            // Print the leftmost column
            for (int i = down; i >= top; --i) {
                System.out.print(matrix[i][left] + " ");
            }
            left++;
            if (top > down || left > right) {
                break;
            }
        }
    }

    public static void maxNonContiguousSubArraySum(int[] nums) {
        System.out.println("maxNonContiguousSubArraySum");
        int arrayLength = nums.length;
        int currMaxSumInclCurrValue = nums[0];
        int currMaxSumExclCurrValue = 0;
        int currMaxTempSum = 0;
        for (int i = 1; i < arrayLength; i++) {
            currMaxTempSum = Math.max(currMaxSumInclCurrValue,
                    currMaxSumExclCurrValue);
            currMaxSumInclCurrValue = currMaxSumExclCurrValue + nums[i];
            currMaxSumExclCurrValue = currMaxTempSum;
        }
        int result = Math.max(currMaxSumInclCurrValue, currMaxSumExclCurrValue);
        System.out.println(result);
        System.out.println();
    }

    public static void maxContiguousSubArraySum(int[] nums) {
        System.out.println("maxContiguousSubArraySum");
        int currentSum = 0;
        int absoluteSum = 0;
        int startIndex = -1;
        int endIndex = -1;
        int tempIndex = -1;
        for (int i = 0; i < nums.length; i++) {
            currentSum = currentSum + nums[i];
            if (currentSum < 0) {
                currentSum = 0;
                tempIndex = i + 1;
            }

            if (currentSum > absoluteSum) {
                absoluteSum = currentSum;
                startIndex = tempIndex;
                endIndex = i;
            }
        }
        System.out.println("startIndex = " + startIndex + " endIndex = "
                + endIndex);
        System.out.println("absoluteSum = " + absoluteSum);
        System.out.println();
    }

    public static void print2Sum(int[] nums, int sum) {
        System.out.println("print2SumWithoutUsingExtraSpace");
        Arrays.sort(nums);
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            if (nums[left] + nums[right] == sum) {
                System.out.print("(" + nums[left] + ", " + nums[right] + ")");
                left++;
                right--;
            } else if (nums[left] + nums[right] < sum) {
                left++;
            } else {
                right--;
            }
        }
        System.out.println();
    }

    public static void print3Sum(int[] nums, int sum) {
        System.out.println("print3SumWithoutUsingExtraSpace");
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                if (nums[i] + nums[left] + nums[right] == sum) {
                    System.out.print("(" + nums[i] + ", " + nums[left] + ", "
                            + nums[right] + ")");
                    left++;
                    right--;
                } else if (nums[i] + nums[left] + nums[right] < sum) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        System.out.println();
    }

    public static void print2SumUsingExtraSpace(int[] nums, int sum) {
        System.out.println("print2SumUsingExtraSpace");
        Set<Integer> intSet = new HashSet<Integer>();
        for (int i : nums) {
            if (intSet.contains(sum - i)) {
                System.out.print("(" + i + ", " + (sum - i) + ")");
            } else {
                intSet.add(i);
            }
        }
        System.out.println();
    }

    public static void print3SumUsingExtraSpace(int[] nums, int sum) {
        System.out.println("print3SumUsingExtraSpace");
        Set<Integer> intSet = new HashSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            intSet.add(i);
            for (int j = 0; j < nums.length; j++) {
                if (intSet.contains(sum - (nums[i] + nums[j]))) {
                    System.out.print("(" + nums[i] + ", " + nums[j] + ", "
                            + (sum - (nums[i] + nums[j])) + ")");
                }
            }
        }
        System.out.println();
    }

    public static void printTwoMaxNumbers(int[] nums) {
        int maxOne = 0;
        int maxTwo = 0;
        for (int n : nums) {
            if (maxOne < n) {
                maxTwo = maxOne;
                maxOne = n;
            } else if (maxTwo < n) {
                maxTwo = n;
            }
        }
        System.out.println("First Max Number: " + maxOne);
        System.out.println("Second Max Number: " + maxTwo);
    }

    public static void printThreeMaxNumbers(int[] nums) {
        int maxOne = 0;
        int maxTwo = 0;
        int maxThree = 0;
        for (int n : nums) {
            if (maxThree > n) {
                continue;
            }

            if (maxOne < n) {
                maxThree = maxTwo;
                maxTwo = maxOne;
                maxOne = n;
            } else if (maxTwo < n) {
                maxThree = maxTwo;
                maxTwo = n;
            } else if (maxThree < n) {
                maxThree = n;
            }
        }
        System.out.println("First Max Number: " + maxOne);
        System.out.println("Second Max Number: " + maxTwo);
        System.out.println("Third Max Number: " + maxThree);
    }

    public static void sort01(int[] a) {
        int left = 0;
        int right = a.length - 1;
        while (left < right) {
            if (a[left] == 0) {
                left++;
            } else {
                swap(a, left, right);
                right--;
            }
        }
    }

    public static void sort012(int[] a) {
        int left = 0;
        int curr = 0;
        int right = a.length - 1;
        while (curr < right) {
            if (a[curr] == 0) {
                swap(a, left, curr);
                left++;
                curr++;
            } else if (a[curr] == 2) {
                swap(a, curr, right);
                right--;
            } else {
                curr++;
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int old = a[i];
        a[i] = a[j];
        a[j] = old;
    }

    public static <T> void populatePowerSet(Set<List<T>> subsetsOfInputList,
                                            List<T> inputList) {
        subsetsOfInputList.add(inputList);
        for (int i = 0; i < inputList.size(); i++) {
            List<T> tempList = new ArrayList<T>(inputList);
            tempList.remove(i);
            populatePowerSet(subsetsOfInputList, tempList);
        }
    }

    public static <T> void populatePowerSetOptimized(
            Set<List<T>> subsetsOfInputList, List<T> inputList) {
        List<T> outputList = new ArrayList<>();
        populatePowerSetOptimized(subsetsOfInputList, inputList, outputList, 0);
        subsetsOfInputList.add(outputList);
    }

    private static <T> void populatePowerSetOptimized(
            Set<List<T>> subsetsOfInputList, List<T> inputList,
            List<T> outputList, int curIndex) {
        subsetsOfInputList.add(new ArrayList<T>(outputList));
        int loopNum = curIndex + 1;
        System.out.println("Start processing of loop: " + loopNum);
        System.out.println();
        System.out.println();
        for (int i = curIndex; i < inputList.size(); i++) {
            System.out.println("Added: " + inputList.get(i) + " for i= " + i
                    + " within loop = " + loopNum);
            System.out.println();
            outputList.add(inputList.get(i));
            populatePowerSetOptimized(subsetsOfInputList, inputList,
                    outputList, i + 1);
            System.out.println("Removed: "
                    + outputList.get(outputList.size() - 1) + " for i= " + i
                    + " within loop = " + loopNum);
            System.out.println();
            outputList.remove(outputList.size() - 1);
        }

        System.out.println();
        System.out.println("End processing of loop: " + loopNum);
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> result = new ArrayList<>();

        if (n <= 1) {
            return result;
        }

        List<Integer> curr = new ArrayList<>();

        getFactorsHelper(2, 1, n, curr, result);

        return result;
    }

    private void getFactorsHelper(int start, int product, int n,
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
                getFactorsHelper(i, product * i, n, curr, result);
                curr.remove(curr.size() - 1);
            }
        }
    }

    public static String[] ClosestColor(String[] hexcodes) {
        System.out.println(Arrays.toString(hexcodes));
        int[] pureBlack = new int[]{0, 0, 0};
        int[] pureGreen = new int[]{0, 255, 0};
        int[] pureWhite = new int[]{255, 255, 255};
        int[] pureRed = new int[]{255, 0, 0};
        int[] pureBlue = new int[]{0, 0, 255};

        int noOfLines = hexcodes.length;
        String[] result = new String[noOfLines];
        Map<Double, String> map = new TreeMap<Double, String>();
        for (int i = 0; i < noOfLines; i++) {
            map.clear();
            String nextLine = hexcodes[i];
            int r = Integer.parseInt(nextLine.substring(0, 8), 2);
            int g = Integer.parseInt(nextLine.substring(8, 16), 2);
            int b = Integer.parseInt(nextLine.substring(16, 24), 2);
            int[] rgb = new int[]{r, g, b};

            getDistance(pureBlack, rgb, "Black", map);
            getDistance(pureWhite, rgb, "White", map);
            getDistance(pureGreen, rgb, "Green", map);
            getDistance(pureRed, rgb, "Red", map);
            getDistance(pureBlue, rgb, "Blue", map);

            Iterator<Double> itr = map.keySet().iterator();
            result[i] = map.get(itr.next());
        }

        return result;
    }

    private static void getDistance(int[] rgb, int[] rgb1, String color,
                                    Map<Double, String> map) {
        double distance = (int) Math.pow(rgb[0] - rgb1[0], 2)
                + (int) Math.pow(rgb[1] - rgb1[1], 2)
                + (int) Math.pow(rgb[2] - rgb1[2], 2);
        if (map.containsKey(distance)) {
            map.put(distance, "Ambiguous");
        } else {
            map.put(distance, color);
        }
    }

    public static int zombieCluster(String[] zombies) {
        char[][] matrix = new char[zombies.length][zombies[0].length()];
        for (int i = 0; i < zombies.length; i++) {
            String zombie = zombies[0];
            matrix[i] = zombie.toCharArray();
        }

        return numZombies(matrix);
    }

    private static int numZombies(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return 0;

        int m = matrix.length;
        int n = matrix[0].length;

        int numZombiesCluster = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && matrix[i][j] == '1') {
                    numZombiesCluster++;
                    combineZombies(matrix, i, j);
                }
            }
        }

        return numZombiesCluster;
    }

    private static void combineZombies(char[][] matrix, int i, int j) {
        int m = matrix.length;
        int n = matrix[0].length;

        if (i < 0 || i >= m || j < 0 || j >= n || i == j || matrix[i][j] != '1')
            return;

        matrix[i][j] = 'v';

        combineZombies(matrix, i - 1, j);
        combineZombies(matrix, i + 1, j);
        combineZombies(matrix, i, j - 1);
        combineZombies(matrix, i, j + 1);

        combineZombies(matrix, i - 1, j - 1);
        combineZombies(matrix, i + 1, j + 1);
        combineZombies(matrix, i + 1, j + 1);
        combineZombies(matrix, i - 1, j - 1);
    }

    public static void getLongestIncreasingSubsequence(int[] arrA) {
        int[] LIS = new int[arrA.length];
        for (int i = 0; i < arrA.length; i++) {
            int max = -1;
            for (int j = 0; j < i; j++) {
                // check if previous elements > current element
                if (arrA[i] > arrA[j]) {
                    // update the max from the previous entries
                    if (max == -1 || max < LIS[j] + 1) {
                        max = 1 + LIS[j];
                    }
                }
            }
            if (max == -1) {
                // means none of the previous element has smaller than arrA[i]
                max = 1;
            }
            LIS[i] = max;
        }
        // find the max in the LIS[]
        int result = -1;
        int index = -1;
        for (int i = 0; i < LIS.length; i++) {
            if (result < LIS[i]) {
                result = LIS[i];
                index = i;
            }
        }
        // Print the result
        // Start moving backwards from the end and
        String path = arrA[index] + " ";
        int res = result - 1;
        for (int i = index - 1; i >= 0; i--) {
            if (LIS[i] == res) {
                path = arrA[i] + " " + path;
                res--;
            }
        }
        System.out.println("Longest Increasing subsequence: " + result);
        System.out.println("Actual Elements: " + path);
    }

    public static int rotatedBinarySearch(int a[], int key) {
        System.out.println("Array : " + a);
        int result = -1;
        int l = 0;
        int r = a.length - 1;
        while (l <= r) {
            // Avoid overflow, same as M=(L+R)/2
            int m = l + ((r - l) / 2);
            if (a[m] == key) {
                result = m;
                break;
            }

            // the bottom half is sorted
            if (a[l] <= a[m]) {
                if (a[l] <= key && key < a[m]) {
                    r = m - 1;
                } else {
                    l = m + 1;
                }
                // the upper half is sorted
            } else {
                if (a[m] < key && key <= a[r]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
        }
        System.out.println("Index of " + key + " = " + result);
        return result;
    }

    public static int findSortedArrayRotation(int a[]) {
        System.out.println("Array : " + a);
        int l = 0;
        int r = a.length - 1;
        while (a[l] > a[r]) {
            int m = l + (r - l) / 2;
            if (a[m] > a[r]) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        System.out.println("Index of rotation = " + l);
        return l;
    }

    public static void testFindCoins() {
        int goal = 50;
        int[] coins = new int[]{25, 10, 5};

        // for formatting the string outputs
        Map<Integer, String> names = new HashMap<Integer, String>();
        names.put(25, "quarter(s)");
        names.put(10, "dime(s)");
        names.put(5, "nickel(s)");

        // to keep track of the current amounts of each type of coin
        Map<String, Integer> curr = new HashMap<String, Integer>();
        curr.put(names.get(25), 0);
        curr.put(names.get(10), 0);
        curr.put(names.get(5), 0);

        findCoins(goal, 0, coins, names, curr);
    }

    public static void testMinimumCoinChangePowerSet() {
        // makeCoinChangeMem(new int[] { 3, 7, 11 }, 18);
        // makeCoinChangeMem(new int[] { 3, 2, 5, 7, 10 }, 27);
        List<Integer> inputAsList = new ArrayList<Integer>();
        inputAsList.add(3);
        inputAsList.add(7);
        inputAsList.add(11);
        minimumCoinChangePowerSet(inputAsList, 18);

        inputAsList.clear();

        inputAsList.add(3);
        inputAsList.add(2);
        inputAsList.add(5);
        inputAsList.add(7);
        inputAsList.add(10);
        minimumCoinChangePowerSet(inputAsList, 27);
    }

    public static void testSubsetSumPowerSet() {
        List<Integer> inputAsList = new ArrayList<Integer>();
        inputAsList.add(2);
        inputAsList.add(4);
        inputAsList.add(7);
        inputAsList.add(11);
        inputAsList.add(3);
        getSubsetSumPowerSet(inputAsList, 3, 100);
    }

    public static void testPopulatePowerSet() {
        List<String> inputAsList = new ArrayList<String>();
        inputAsList.add("a");
        inputAsList.add("b");
        inputAsList.add("c");
        // inputAsList.add("d");
        // inputAsList.add("e");
        Set<List<String>> subsetsOfInputList = new LinkedHashSet<List<String>>();
        populatePowerSet(subsetsOfInputList, inputAsList);
        System.out.println("subsetsOfInputList.size = "
                + subsetsOfInputList.size());
        System.out.println("subsetsOfInputList = " + subsetsOfInputList);
    }

    public static void testPopulatePowerSetOptimized() {
        List<String> inputAsList = new ArrayList<String>();
        inputAsList.add("a");
        inputAsList.add("b");
        inputAsList.add("c");
        // inputAsList.add("d");
        // inputAsList.add("e");
        Set<List<String>> subsetsOfInputList = new LinkedHashSet<List<String>>();
        populatePowerSetOptimized(subsetsOfInputList, inputAsList);
        System.out.println("subsetsOfInputList.size = "
                + subsetsOfInputList.size());
        System.out.println("subsetsOfInputList = " + subsetsOfInputList);
    }

    public static void testLIS() {
        int[] A = {1, 12, 7, 0, 23, 11, 52, 31, 61, 69, 70, 2};
        getLongestIncreasingSubsequence(A);
        System.out.println();
        System.out.println();
    }

    public static void printMax() {
        int num[] = {5, 34, 78, 2, 45, 1, 99, 23};
        printTwoMaxNumbers(num);
        System.out.println();
        printThreeMaxNumbers(num);
        System.out.println();
    }

    public static void printSum() {
        int nums[] = {5, 6, 8, 2, 7, 11, 1, 4};
        print2SumUsingExtraSpace(nums, 12);
        System.out.println();
        print2Sum(nums, 12);
        System.out.println();
        int nums1[] = {5, 6, 8, 2, 7, 11, 1, 4};
        print3SumUsingExtraSpace(nums1, 12);
        System.out.println();
        print3Sum(nums1, 12);
        System.out.println();
        int nums2[] = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        maxContiguousSubArraySum(nums2);
        System.out.println();
        int nums3[] = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        maxNonContiguousSubArraySum(nums3);
        System.out.println();
    }

    public static void printMaxProduct() {
        int[] numbers = new int[]{-3, -4, -1, 0, 2, 4, 5, 7, 3, -2, 6};
        int result = getMaxProductOfThreeNumbers(numbers);
        System.out.println("Result : " + result);
        numbers = new int[]{-10, -11, 0, 2, 4, 5, 7, 3, 6, 1};
        result = getMaxProductOfThreeNumbers(numbers);
        System.out.println("Result : " + result);
        numbers = new int[]{-10, -11, -9, 0, 2, 4, 5, 7, 3, 6, 1};
        result = getMaxProductOfThreeNumbers(numbers);
        System.out.println("Result : " + result);
    }

    // ---------------------------------

    public ArrayList<Integer> maxset(ArrayList<Integer> a) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int currentSum = 0;
        int absoluteSum = 0;
        int startIndex = -1;
        int endIndex = -1;
        int tempIndex = -1;
        for (int i = 0; i < a.size(); i++) {
            currentSum = currentSum + a.get(i);
            if (currentSum < 0) {
                currentSum = 0;
                tempIndex = i + 1;
            }

            if (currentSum > absoluteSum) {
                absoluteSum = currentSum;
                startIndex = tempIndex;
                endIndex = i;
            }
        }

        if (startIndex < 0 && endIndex < 0) {
            return result;
        } else if (endIndex >= 0) {
            if (startIndex < 0) {
                startIndex = 0;
            }
            for (int i = startIndex; i <= endIndex; i++) {
                result.add(a.get(i));
            }
        }
        return result;
    }

    public static int solution(int N) {
        String binaryStr = Integer.toBinaryString(N);
        System.out.println(binaryStr);
        char[] binaryValues = binaryStr.toCharArray();
        boolean firstOccurOfOne = false;
        boolean secondOccurOfOne = false;
        int absoluteCount = 0;
        int count = 0;
        for (char c : binaryValues) {
            if (c == '1') {
                if (!firstOccurOfOne) {
                    firstOccurOfOne = true;
                } else {
                    if (!secondOccurOfOne) {
                        secondOccurOfOne = true;
                    }

                    if (secondOccurOfOne) {
                        firstOccurOfOne = false;
                        secondOccurOfOne = false;
                        if (count > absoluteCount) {
                            absoluteCount = count;
                            count = 0;
                        }
                    }
                }
            } else {
                if (firstOccurOfOne) {
                    count++;
                }
            }
        }
        return absoluteCount;
    }
}
