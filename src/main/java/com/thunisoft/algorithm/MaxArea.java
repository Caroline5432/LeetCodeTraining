package com.thunisoft.algorithm;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/10/14.
 */
public class MaxArea {

    // 时间复杂度 O（n ^ 2）
    public static int maxArea(int[] height) {
        int result = 0;
        int len = height.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                result = Math.max(result, Math.min(height[i], height[j]) * (j - i));
            }
        }
        return result;
    }

    // 时间复杂度 O（n）
    public static int maxArea2(int[] height) {
        int result = 0;
        int len = height.length;
        for (int i = 0, j = len - 1; i < len && j > 0;) {
            result = Math.max(result, Math.min(height[i], height[j]) * (j - i));
            if(height[i] > height[j]) {
                j--;
            } else {
                i++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] height = {2,3,4,5,18,17,6};
        System.out.println(maxArea2(height));
    }

}
