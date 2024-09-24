package com.training.algorithm.search;

import java.math.BigDecimal;

/**
 * @ClassName BinarySearch
 * @Description 二分查找
 * @Author caroline
 * @Version 1.0
 * @Date 2024/8/6 11:39
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 5;
        int index = binarySearch(arr, target);
        System.out.println(index);

        System.out.println(bSquar(6));
    }

    /**
     * 实际上 mid=(low+high)/2 这种写法是有问题的。
     * 因为如果 low 和 high 比较大的话，两者之和就有可能会溢出。
     * 改进的方法是将 mid 的计算方式写成 low+(high-low)/2。
     * 更进一步，如果要将性能优化到极致的话，我们可以将这里的除以 2 操作转化成位运算 low+((high-low)>>1)。
     * 因为相比除法运算来说，计算机处理位运算要快得多。
     */
    private static int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }

    /**
     * 递归实现二分法查找
     */
    private static int binarySearch(int[] arr, int target, int low, int high) {
        if (low > high) {
            return -1;
        }
        int mid = (low + high) / 2;
        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] > target) {
            return binarySearch(arr, target, low, mid - 1);
        } else {
            return binarySearch(arr, target, mid + 1, high);
        }
    }

    /**
     * 求一个数的平方根，要求精确到小数6位
     */
    private static double bSquar(int x) {
        double low = 0;
        double newNum = x * Math.pow(10, 12);
        double high = newNum;
        double ans = -1;
        while (high >= low) {
            double mid = low + (high -low) / 2;
            if (mid > newNum / mid) {
                high = mid - 1;
            } else {
                ans = mid;
                low = mid + 1;
            }
        }
        double v = ans / Math.pow(10, 6) * 1.0;
        BigDecimal bd = new BigDecimal(v);
        return bd.setScale(6, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

}
