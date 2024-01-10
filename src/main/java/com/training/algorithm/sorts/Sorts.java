package com.training.algorithm.sorts;

import java.util.Arrays;

/**
 * @ClassName BubbleSorted
 * @Description 冒泡排序、插入排序、选择排序
 * @Author caroline
 * @Version 1.0
 * @Date 2024/1/10 13:23
 */
public class Sorts {

    public static void main(String[] args) {
        int[] nums = new int[]{4,6,5,3,2,7,1};
        selectionSort(nums, 7);
        Arrays.stream(nums).forEach(num -> System.out.print(num + " "));
    }

    private static void selectionSort (int[] nums, int n) {
        if (n <= 1) {
            return;
        }

        for (int i = 0; i < n; i++) {
            // 找出未排序数组中的最小值
            int minIndex = i;
            for (int j = i; j < n; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }
            // 交换数据
            int temp = nums[minIndex];
            nums[minIndex] = nums[i];
            nums[i] = temp;
        }

    }
    private static void insertionSort (int[] nums, int n) {
        if (n <= 1) {
            return;
        }

        for (int i = 1; i < n; i++) {
            int value = nums[i];
            int j = i-1;

            // 查找插入的位置
            for (;j >= 0; j--) {
                if (nums[j] > value) {
                    // 数据移动
                    nums[j+1] = nums[j];
                } else {
                    break;
                }
            }
            nums[j+1] = value;
        }
    }

    private static void bubbleSort (int[] nums, int n) {
        if (n <= 1) {
            return;
        }

        for (int i = 0; i < n; i++) {
            // 提前退出冒泡排序的标记
            boolean flag = false;
            for (int j = 0; j < n-i-1; j++) {
                if (nums[j] > nums[j+1]) {
                    int temp = nums[j+1];
                    nums[j+1] = nums[j];
                    nums[j] = temp;
                    // 表示存在数据交换
                    flag = true;
                }
            }
            // 没有数据交换，提前退出
            if (!flag) {
                break;
            }
        }
    }

}
