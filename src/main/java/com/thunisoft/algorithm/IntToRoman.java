package com.thunisoft.algorithm;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/10/14.
 */
public class IntToRoman {

    //贪心算法 时间复杂度 O(1) 空间复杂度 O(1)
    public static String intToRoman(int num) {
        int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romans = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder roman = new StringBuilder();
        int index = 0;
        while (index < 13){
            while (num >= nums[index]) {
                roman.append(romans[index]);
                num -= nums[index];
            }
            index ++;
        }
        return roman.toString();
    }

    public static void main(String[] args) {
        System.out.println(intToRoman(40));
    }

}
