package com.training.algorithm.test;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/10/9.
 */
public class IsPalindrome {
    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int y = 0;
        int temp = x;
        while (temp != 0) {
            y = y * 10 + (temp % 10);
            temp /= 10;
        }
        if (x == y) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(12321));
    }
}
