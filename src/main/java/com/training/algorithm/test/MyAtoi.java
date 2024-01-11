package com.training.algorithm.test;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/10/8.
 */
public class MyAtoi {

    public static int myAtoi(String str) {
        int i = 0, j = 0, len = str.length();
        boolean negative = false;
        int num = 0;
        for (i = 0; i < len; i++) {
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                break;
            } else if (str.charAt(i) == '-' || str.charAt(i) == '+') {
                negative = str.charAt(i) == '-';
                i++;
                break;
            } else if (str.charAt(i) != ' ') {
                return 0;
            }
        }
        for (j = i; j < len; j++) {
            if (str.charAt(j) < '0' || str.charAt(j) > '9') {
                break;
            }
        }
        for (int x = i; x < j; x++) {
            if (negative) {
                if (num > Integer.MAX_VALUE / 10 || (num == Integer.MAX_VALUE / 10 && str.charAt(x) - '0' > 8)) {
                    return Integer.MIN_VALUE;
                }
                num = num * 10 + (str.charAt(x) - '0');
            } else {
                if (num > Integer.MAX_VALUE / 10 || (num == Integer.MAX_VALUE / 10 && str.charAt(x) - '0' > 7)) {
                    return Integer.MAX_VALUE;
                }
                num = num * 10 + (str.charAt(x) - '0');
            }
        }
        if (negative) {
            num *= -1;
        }
        return num;
    }

    public static void main(String[] args) {
        System.out.println(myAtoi("-2147483647"));
    }

}
