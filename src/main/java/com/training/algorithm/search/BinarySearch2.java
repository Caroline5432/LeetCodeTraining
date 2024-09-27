package com.training.algorithm.search;

/**
 * @ClassName BinarySearch2
 * @Description
 * @Author caroline
 * @Version 1.0
 * @Date 2024/9/25 15:14
 */
public class BinarySearch2 {
    public static void main(String[] args) {
        int[] num = {4,5,6,7,8,1,2,3};
        System.out.println(binarySearch5(num, 10));
    }

    /**
     * 查找第一个值等于给定值的元素
     */
    private static int binarySearch1(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                if (mid == 0 || nums[mid - 1] != target) {
                    return mid;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 查找最后一个值等于给定值的元素
     */
    private static int binarySearch2(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                if (mid == nums.length - 1 || nums[mid + 1] != target) {
                    return mid;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }

    /**
     * 查找第一个大于等于给定值的元素
     */
    private static int binarySearch3(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (mid == 0 || nums[mid - 1] < target) {
                return mid;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 查找最后一个小于等于给定值的元素
     */
    private static int binarySearch4(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (mid == nums.length - 1 || nums[mid + 1] > target) {
                return mid;
            } else {
                left = mid + 1;
            }
        }
        return -1;
   }

   /**
    * 如果有序数组是一个循环有序数组，比如 4，5，6，1，2，3。
    * 针对这种情况，如何实现一个求“值等于给定值”的二分查找算法呢？
    */
   private static int binarySearch5(int[] nums, int target) {
       int left = 0;
       int right = nums.length - 1;
       int mid;
       while (left <= right) {
           mid = left + (right - left) / 2;
          if (nums[mid] == target) {
              return mid;
          } else if (nums[mid] >= nums[0]) {
              // 前半部分有序，后半部分无序
              if (target >= nums[0] && target < nums[mid]) {
                  right = mid - 1;
              } else {
                  left = mid + 1;
              }
          } else {
              // 后半部分有序，前半部分无序
              if (target > nums[mid] && target <= nums[nums.length - 1]) {
                  left = mid + 1;
              } else {
                  right = mid - 1;
              }
          }
       }
       return -1;
   }
}
