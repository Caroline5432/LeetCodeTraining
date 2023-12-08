package com.training.algorithm;

public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {8, 9, 2, 5, 4, 7, 1, 3, 6};
        quickSort(arr, 0, arr.length-1);
        for (int num : arr) {
            System.out.println(num);
        }
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotpos = partition(arr, low, high);
            quickSort(arr, low, pivotpos - 1);
            quickSort(arr, pivotpos + 1, high);
        }
    }

    private static int partition (int[] arr, int low, int high) {
        int pivot = arr[low];
        while (low < high) {
            while (low < high && arr[high] >= pivot) {
                high--;
            }
            arr[low] = arr[high];
            while (low < high && arr[low] <= pivot) {
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = pivot;
        return low;
    }

}
