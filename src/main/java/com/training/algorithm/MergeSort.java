package com.training.algorithm;

public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {8, 9, 2, 5, 4, 7, 1, 3, 6};
        mergeSort(arr, 0, arr.length - 1);
        for (int num : arr) {
            System.out.println(num);
        }
    }

    public static void mergeSort(int[] arr, int left, int right) {
       if (left < right) {
           int mid = (left + right) / 2;
           mergeSort(arr, left, mid);
           mergeSort(arr, mid+1, right);
           merge(arr, left, mid, right);
       }
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int s1 = left;
        int s2 = mid + 1;
        int[] temp = new int[right-left+1];
        int i = 0;

        while (s1 <= mid && s2 <= right) {
            if (arr[s1] <= arr[s2]) {
                temp[i++] = arr[s1++];
            } else {
                temp[i++] = arr[s2++];
            }
        }

        while (s1 <= mid) {
            temp[i++] = arr[s1++];
        }

        while (s2 <= right) {
            temp[i++] = arr[s2++];
        }

        for (int j = 0; j < temp.length; j++) {
            arr[j+left] = temp[j];
        }
    }
}
