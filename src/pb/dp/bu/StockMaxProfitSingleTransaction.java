package pb.dp.bu;

public class StockMaxProfitSingleTransaction {

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int n = prices.length;
        int[] dp = new int[n];
        dp[0] = 0;
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], prices[i] - prices[i - 1]);
        }
        return dp[n - 1];
    }
}
