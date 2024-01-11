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
        quickSort(nums, 0,nums.length - 1);
        Arrays.stream(nums).forEach(num -> System.out.print(num + " "));
        System.out.println();

        int k = findKByQuickSort(nums, 0, nums.length - 1, 4);
        System.out.println(k);
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

    /**
     * 查找 O(n) 时间复杂度内求无序数组中的第 K 大元素。比如，4， 2， 5， 12， 3 这样一组数据，第 3 大元素就是 4。
     * 我们选择数组区间 A[0...n-1]的最后一个元素 A[n-1]作为 pivot，对数组 A[0...n-1]原地分区，
     * 这样数组就分成了三部分，A[0...p-1]、A[p]、A[p+1...n-1]。
     * 如果 p+1=K，那 A[p]就是要求解的元素；如果 K>p+1, 说明第 K 大元素出现在 A[p+1...n-1]区间，
     * 我们再按照上面的思路递归地在 A[p+1...n-1]这个区间内查找。
     * 同理，如果 K < p+1,则第K大元素出现在A[0...p-1]。
     * 递归公式：p = position(0,n)
     * 终止条件：K = p+1
     */
    private static int findKByQuickSort (int[] arr, int start, int end, int k) {
        int pivot = position(arr, start, end);
        if (k == pivot + 1) {
            return arr[pivot];
        } else if (k < pivot + 1) {
            return findKByQuickSort(arr, start, pivot - 1, k);
        } else {
            return findKByQuickSort(arr, pivot + 1, end, k);
        }

    }

}
