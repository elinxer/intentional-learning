package dddy.gin.leetcode.explore.easy;

import static dddy.gin.leetcode.explore.easy.Const.C2;

/**
 * 题目详情:https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/22/
 *
 * @author gin
 */
public class Array02 {
    /**
     * 方法一：利用数学思想，最大利润为后一天减去前一天并且大于0的所有数的和
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < C2) {
            return 0;
        }

        int sum = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            int profit = prices[i + 1] - prices[i];
            if (profit > 0) {
                sum += profit;
            }
        }
        return sum;
    }


    public static void main(String[] args) {
        Array02 a = new Array02();
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println(a.maxProfit(prices));
    }
}
