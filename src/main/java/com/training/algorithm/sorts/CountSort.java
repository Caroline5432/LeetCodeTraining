package com.training.algorithm.sorts;

/**
 * @ClassName CountSort
 * @Description 计数排序
 * 计数排序只能用在数据范围不大的场景中，如果数据范围 k 比要排序的数据 n 大很多，就不适合用计数排序了。
 * 而且，计数排序只能给非负整数排序，如果要排序的数据是其他类型的，要将其在不改变相对大小的情况下，转化为非负整数。
 * 比如，还是拿考生这个例子。如果考生成绩精确到小数后一位，我们就需要将所有的分数都先乘以 10，转化成整数，然后再放到 9010 个桶内。
 * 再比如，如果要排序的数据中有负数，数据的范围是[-1000, 1000]，那我们就需要先对每个数据都加 1000，转化成非负整数。
 * @Author caroline
 * @Version 1.0
 * @Date 2024/8/5 14:21
 */
public class CountSort {

    public static void main(String[] args) {
        int[] arr = {1, 3, 7, 3, 10, 1, 3, 6, 2, 3, 8, 1};
        countSort(arr, 12);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    // 计数排序，a是数组，n是数组大小。假设数组中存储的都是非负整数。
    private static void countSort(int[] arr, int n) {
        if (n <= 1) {return;}
        // 确定数据范围
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        // 创建计数数组
        int[] count = new int[max + 1];
        for (int i = 0; i < max + 1; i++) {
            count[i] = 0;
        }

        // 计算每个元素的个数，放入count中
        for (int i = 0; i < n; i++) {
            count[arr[i]]++;
        }

        // 依次累加
        for (int i = 1; i < max + 1; i++) {
            count[i] += count[i - 1];
        }

        // 申请临时数组r,存储排序之后的数组
        int[] r = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            r[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }

        // 将临时数组r中的元素复制到原数组arr中
        for (int i = 0; i < n; i++) {
            arr[i] = r[i];
        }
    }

}
