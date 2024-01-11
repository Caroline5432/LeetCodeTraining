package com.training.algorithm.test;

import java.util.Arrays;

/**
 * 下⾯的数组代表着苹果期货在 9 天内的价格变化，其中第 n 个元素是苹果在第 n 天的价格，
 * 你可以进⾏最多三次交易，设计⼀个算法，9 天内如何赚到最多的钱？价格数组： [8, 9, 2, 5,4, 7, 1, 3, 6]
 */
public class AppleStock {

    /**
     * 1.定义一个二维数组dp,其中dp[i][j+n] 表示第i天第n次持有状态，dp[i][j+n+1]表示第i天第n次不持有状态
     * 2.状态转移公式如下：
     *  dp[i][j+n] = max(dp[i-1][j+n], dp[i-1][j+n-1]-prices[i])
     *  dp[i][j+n+1] = max(dp[i-1][j+n+1], dp[i-1][j+n]+prices[i])
     * 3.初始化状态值如下：
     *  dp[0][0] = 0
     *  dp[0][n] = -prices[i]
     *  dp[0][n+1] = 0
     * @param k 最多进行k次交易
     * @param prices 价格数组
     * @return
     */
    public static int maxProfit(int k, int[] prices) {
        if (prices.length == 0) {
            return 0;
        }

        int n = prices.length;
        int[][] dp = new int[n][2*k+1];
        for (int j = 1; j < 2*k; j=j+2) {
            dp[0][0] = 0;
            dp[0][j] = -prices[0];
            dp[0][j+1] = 0;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < 2*k; j=j+2) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j-1] - prices[i]);
                dp[i][j+1] = Math.max(dp[i - 1][j+1], dp[i - 1][j] + prices[i]);
            }
        }

        return dp[n - 1][2*k];
    }

    public static void main(String[] args) {
        int[] prices = {8, 9, 2, 5, 4, 7, 1, 3, 6};
        int fee = 3;
        System.out.println(maxProfit(fee, prices));
        System.out.println(maxProfit2(fee, prices));
    }

    public static int maxProfit2(int k, int[] prices) {
        if (prices.length == 0) {
            return 0;
        }

        int n = prices.length;
        k = Math.min(k, n / 2);
        int[][] buy = new int[n][k + 1];
        int[][] sell = new int[n][k + 1];

        buy[0][0] = -prices[0];
        sell[0][0] = 0;
        for (int i = 1; i <= k; ++i) {
            buy[0][i] = sell[0][i] = Integer.MIN_VALUE / 2;
        }

        for (int i = 1; i < n; ++i) {
            buy[i][0] = Math.max(buy[i - 1][0], sell[i - 1][0] - prices[i]);
            for (int j = 1; j <= k; ++j) {
                buy[i][j] = Math.max(buy[i - 1][j], sell[i - 1][j] - prices[i]);
                sell[i][j] = Math.max(sell[i - 1][j], buy[i - 1][j - 1] + prices[i]);
            }
        }

        return Arrays.stream(sell[n - 1]).max().getAsInt();
    }

}
