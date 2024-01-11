package com.training.algorithm.sorts;

import java.util.Arrays;

/**
 * @ClassName MergeSort
 * @Description 归并排序
 *  递归公式：merge_sort(p,r) = merge(merge_sort(p,q), merge_sort(q+1,r))
 *  终止条件：p >= r 不再继续分解
 * @Author caroline
 * @Version 1.0
 * @Date 2024/1/11 13:50
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {2, 4, 1, 3, 6, 8, 5};
        mergeSort(arr, 0, arr.length-1);
        Arrays.stream(arr).forEach(num -> System.out.print(num + " "));
    }

    private static void mergeSort(int[] arr, int start, int end) {
        // 终止条件
        if (start >= end) {
            return;
        }
        int mid = (start + end) / 2;
        mergeSort(arr, start, mid);
        mergeSort(arr, mid + 1, end);
        merge(arr, start, mid, end);
    }

    /**
     * 两个有序数组合并规则：
     *  申请一个临时数组 tmp，大小与 A[p...r]相同。
     *  我们用两个游标 i 和 j，分别指向 A[p...q]和 A[q+1...r]的第一个元素。
     *  比较这两个元素 A[i]和 A[j]，如果 A[i]<=A[j]，我们就把 A[i]放入到临时数组 tmp，并且 i 后移一位，
     *  否则将 A[j]放入到数组 tmp，j 后移一位。
     *  继续上述比较过程，直到其中一个子数组中的所有数据都放入临时数组中，再把另一个数组中的数据依次加入到临时数组的末尾，
     *  这个时候，临时数组中存储的就是两个子数组合并之后的结果了。
     *  最后再把临时数组 tmp 中的数据拷贝到原数组 A[p...r]中。
     */
    private static void merge (int[] arr, int start, int mid, int end) {
        int left = start;
        int right = mid + 1;
        int[] tmp = new int[end - start + 1];
        int i = 0;

        while (left <= mid && right <= end) {
            if (arr[left] <= arr[right]) {
                tmp[i++] = arr[left++];
            } else {
                tmp[i++] = arr[right++];
            }
        }

        while (left <= mid) {
            tmp[i++] = arr[left++];
        }

        while (right <= end) {
            tmp[i++] = arr[right++];
        }

        // 拷贝临时数组中到原数组
        for (int j = 0; j < tmp.length; j++) {
            arr[start + j] = tmp[j];
        }
    }
}
