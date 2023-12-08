package com.training.algorithm;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/9/23.
 */
public class Reverse {

    public static int reverse(int x) {
        int y = 0;
        while (x != 0) {
            if (y > Integer.MAX_VALUE / 10 || (y == Integer.MAX_VALUE / 10 && x % 10 > 7)) {
                return 0;
            }
            if (y < Integer.MIN_VALUE / 10 || (y == Integer.MIN_VALUE / 10 && x % 10 < -8)) {
                return 0;
            }
            y = x % 10 + y * 10;
            x = x / 10;
        }
        return y;
    }

    //-2147483412
    public static void main(String[] args) {
        System.out.println(reverse(123));
        // System.out.println(Integer.MIN_VALUE);
    }


}
