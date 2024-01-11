package com.training.algorithm.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/11/6.
 *
 * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？
 * 找出所有满足条件且不重复的四元组
 *
 */
public class FourSum {

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        // 数组为空或长度小于4 直接返回
        if (nums == null || nums.length < 4) {
            return ans;
        }
        //数组排序
        Arrays.sort(nums);
        int length = nums.length;
        for (int k = 0; k < length - 3; k++) {
            //当k的值与前面的值相等时忽略
            if (k > 0 && nums[k] == nums[k-1]) continue;
            //获取当前最小值，若比目标值大，说明后面会越来越大，直接结束
            int minSum = nums[k] + nums[k+1] + nums[k+2] + nums[k+3];
            if (minSum > target) break;
            //获取当前最大值，若比目标值小，说明后面会越来越小，忽略
            int maxSum = nums[k] + nums[length-3] + nums[length-2] + nums[length-1];
            if (maxSum < target) continue;

            //第二层循环i,初始值是k+1
            for (int i = k + 1; i < length - 2; i++) {
              //当i的值与前面的值相等时忽略
              if (i > k+1 && nums[i] == nums[i-1]) continue;
              int j = i + 1;
              int h = length - 1;
                //获取当前最小值，若比目标值大，说明后面会越来越大，忽略
                int minSum2 = nums[k] + nums[i] + nums[j] + nums[j+1];
                if (minSum2 > target) continue;
                //获取当前最大值，若比目标值小，说明后面会越来越小，忽略
                int maxSum2 = nums[k] + nums[i] + nums[h-1] + nums[h];
                if (maxSum2 < target) continue;
                while (j < h) {
                    int sum = nums[k] + nums[i] + nums[j] + nums[h];
                    if (sum == target) {
                        ans.add(Arrays.asList(nums[k], nums[i], nums[j], nums[h]));
                        j++;
                        while (j < h && nums[j] == nums[j - 1]) {
                            j++;
                        }
                        h--;
                        while (j < h && nums[h] == nums[h + 1]) {
                            h--;
                        }
                    } else if (sum < target) {
                        j++;
                    } else {
                        h--;
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {1, 0, -1, 0, -2, 2};
        int target = 0;
        System.out.println(fourSum(nums, target));
    }

}
