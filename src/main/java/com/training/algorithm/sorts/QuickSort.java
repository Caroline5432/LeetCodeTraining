package com.training.algorithm.sorts;

import java.util.Arrays;

/**
 * @ClassName QuickSort
 * @Description 快速排序
 * 递推公式：qucik_sort(p,r) = quick_sort(p,q-1) + quick_sort(q+1, r)
 * 终止条件：p >= r
 * @Author caroline
 * @Version 1.0
 * @Date 2024/1/11 14:26
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] nums = new int[]{4,6,5,3,2,7,1};
        quickSort(nums, 0,nums.length-1);
        Arrays.stream(nums).forEach(num -> System.out.print(num + " "));
    }

    private static void quickSort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        int pivot = position(arr, start, end);
        quickSort(arr, start, pivot - 1);
        quickSort(arr, pivot + 1, end);
    }

    /**
     * 随机选择一个元素作为 pivot（一般情况下，可以选择 p 到 r 区间的最后一个元素），
     * 然后对 A[p...r]分区，函数返回 pivot 的下标。
     * 原地分区函数的实现思路:
     * 我们通过游标 i 把 A[p...r-1]分成两部分。
     * A[p...i-1]的元素都是小于 pivot 的，我们暂且叫它“已处理区间”，A[i...r-1]是“未处理区间”。
     * 我们每次都从未处理的区间 A[i...r-1]中取一个元素 A[j]，与 pivot 对比，
     * 如果小于 pivot，则将其加入到已处理区间的尾部，也就是 A[i]的位置。
     */
    private static int position(int[] arr, int start, int end) {
        int pivot = arr[end];
        int i = start;
        for (int j = start; j <= end - 1; j++) {
            if (arr[j] < pivot) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
            }
        }

        int temp = arr[i];
        arr[i] = arr[end];
        arr[end] = temp;

        return i;
    }

}
