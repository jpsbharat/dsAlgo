package pb.gen;

public class StockSingleSell {

    public static void maxProfit(int[] prices) {
        int profit = -1;
        int buyDateIndex = prices[0];
        int sellDateIndex = prices[0];
        for (int i = 0; i < prices.length; i++) {
            for (int j = i; j < prices.length; j++) {
                if (prices[j] > prices[i] && (prices[j] - prices[i] > profit)) {
                    profit = prices[j] - prices[i];
                    buyDateIndex = i;
                    sellDateIndex = j;
                }
            }
        }
        System.out.println("Maximum Profit: " + profit + ", buy date index: "
                + buyDateIndex + ", sell date index: " + sellDateIndex);
    }

    public static void maxProfitDP(int[] prices) {
        int period = prices.length;
        int buyDateIndex = 0;
        int tempIndex = 0;
        int sellDateIndex = 0;
        int current_profit = 0;
        int max_sell_price = prices[period - 1]; // assign the last element
        for (int i = period - 2; i > 0; i--) {
            if (max_sell_price < prices[i]) {
                max_sell_price = prices[i];
                tempIndex = i;
            } else if (max_sell_price > prices[i]) {
                if (current_profit < max_sell_price - prices[i]) {
                    current_profit = max_sell_price - prices[i];
                    buyDateIndex = i;
                    sellDateIndex = tempIndex;
                }
            }
        }
        System.out.println("Maximum Profit(DP): " + current_profit
                + ", buy date index: " + buyDateIndex + ", sell date index: "
                + sellDateIndex);
    }

    public Result maxProfitDC(int[] prices, int start, int end) {
        if (start >= end) {
            return new Result(0, 0, 0);
        }
        int mid = start + (end - start) / 2;
        Result leftResult = maxProfitDC(prices, start, mid);
        Result rightResult = maxProfitDC(prices, mid + 1, end);
        int minLeftIndex = getMinIndex(prices, start, mid);
        int maxRightIndex = getMaxIndex(prices, mid, end);
        int centerProfit = prices[maxRightIndex] - prices[minLeftIndex];
        if (centerProfit > leftResult.profit
                && centerProfit > rightResult.profit) {
            return new Result(centerProfit, minLeftIndex, maxRightIndex);
        } else if (leftResult.profit > centerProfit
                && rightResult.profit > centerProfit) {
            return leftResult;
        } else {
            return rightResult;
        }
    }

    public int getMinIndex(int[] A, int i, int j) {
        int min = i;
        for (int k = i + 1; k <= j; k++) {
            if (A[k] < A[min])
                min = k;
        }
        return min;
    }

    public int getMaxIndex(int[] A, int i, int j) {
        int max = i;
        for (int k = i + 1; k <= j; k++) {
            if (A[k] > A[max])
                max = k;
        }
        return max;
    }

    class Result {
        int profit = 0;
        int buyDateIndex = 0;
        int sellDateIndex = 0;

        public Result(int profit, int buyDateIndex, int sellDateIndex) {
            this.profit = profit;
            this.buyDateIndex = buyDateIndex;
            this.sellDateIndex = sellDateIndex;
        }
    }
}
