package com.training.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定⼀组正整数数组M，找出M数组中N项和为给定数S。如果有多对N项数字的和都等于S，则输出N个数的乘积最⼩的那⼀项，没有则返回空。
 */
public class NTermSum {

    /**
     * 遍历结果列表，选择乘积最小的那一项返回
     * @param numArr 正整数数组
     * @param n N项
     * @param sum 定数和
     * @return 乘积最小的那一项
     */
    public static List<Integer> findMinProduct(int[] numArr, int n, int sum) {
        List<List<Integer>> result = new ArrayList<>();
        findCombination(numArr, n, sum, 0, new ArrayList<>(), result);

        if (result.isEmpty()) {
            return null;
        }

        int minProduct = -1;
        List<Integer> minProductList = null;
        for (List<Integer> list : result) {
            int product = 1;
            for (int num : list) {
                product *= num;
            }
            if (product < minProduct || minProduct == -1) {
                minProduct = product;
                minProductList = list;
            }
        }

        return minProductList;
    }

    /**
     * 遍历正整数数组M，使用递归的方法找到所有N项和为S的组合，并将它们存储在一个名为result的列表中
     * @param numArr 正整数数组
     * @param n N项
     * @param sum 定数和
     * @param start 索引
     * @param current 遍历中的数组
     * @param result
     */
    private static void findCombination (int[] numArr, int n, int sum, int start, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == n) {
            if (sum == 0) {
                result.add(new ArrayList<>(current));
            }
            return;
        }

        for (int i = start; i < numArr.length; i++) {
            if (sum - numArr[i] >= 0) {
                current.add(numArr[i]);
                findCombination(numArr, n, sum - numArr[i], i + 1, current, result);
                current.remove(current.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        int[] numArr = {0, 1, 2, 3, 4, 5, 6};
        int n = 2;
        int sum = 6;
        System.out.println(findMinProduct(numArr, n, sum));
    }

}
