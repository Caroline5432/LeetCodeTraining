package com.training.algorithm.test;

import java.util.Arrays;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/11/6.
 */
public class ThreeSumClosest {

    public static int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        //排序
        Arrays.sort(nums);
        int sum = nums[0] + nums[1] + nums[2];
        int temp = 0;
        int len = nums.length;
        for (int i = 0; i < len - 2; i++) {
            int L = i + 1;
            int R = len - 1;
            while (L < R) {
                temp = nums[i] + nums[L] + nums[R];
                /*if(Math.abs(temp - target) < Math.abs(sum - target)) {
                    sum = temp;
                    if(temp > target) {
                        R--;
                    } else {
                        L++;
                    }
                } else if(temp > target) {
                    R--;
                } else {
                    L++;
                }*/

                if (temp > target) {
                    if(Math.abs(temp - target) < Math.abs(sum - target)) {
                        sum = temp;
                    }
                    R--;
                } else {
                    if(Math.abs(temp - target) < Math.abs(sum - target)) {
                        sum = temp;
                    }
                    L++;
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int[] nums = {6,-18,-20,-7,-15,9,18,10,1,-20,-17,-19,-3,-5,-19,10,6,-11,1,-17,-15,6,17,-18,-3,16,19,-20,-3,-17,-15,-3,12,1,-9,4,1,12,-2,14,4,-4,19,-20,6,0,-19,18,14,1,-15,-5,14,12,-4,0,-10,6,6,-6,20,-8,-6,5,0,3,10,7,-2,17,20,12,19,-13,-1,10,-1,14,0,7,-3,10,14,14,11,0,-4,-15,-8,3,2,-5,9,10,16,-4,-3,-9,-8,-14,10,6,2,-12,-7,-16,-6,10};
        int target = -52;
        System.out.println(threeSumClosest(nums, target));
    }
}
