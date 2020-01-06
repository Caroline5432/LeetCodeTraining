package com.thunisoft.algorithm;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/9/23.
 */
public class Convert {

    public static String convert(String s, int numRows) {
        int n = s.length();

        if(numRows == 1) {
            return s;
        }

        // int colInt = n / (2 * numRows - 2);
        // int colMod = n % (2 * numRows - 2);
        // int numCol = colInt * (2 * numRows - 2) + (colMod / numRows + colMod % numRows);
        // int[][] arr = new int[numRows][numCol];

        /**
         * 对于所有整数 k，
         *
         * 行 0 中的字符位于索引 k(2⋅numRows−2) 处;
         * 行 numRows−1 中的字符位于索引 k(2⋅numRows−2)+numRows−1 处;
         * 内部的行 i 中的字符位于索引 k(2⋅numRows−2)+i 以及 (k+1)(2⋅numRows−2)−i 处;
         *
         */
        StringBuilder ret = new StringBuilder();
        int cycleLen = 2 * numRows - 2;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j + i < n; j += cycleLen) {
                ret.append(s.charAt(j + i));
                if (i != 0 && i != numRows - 1 && j + cycleLen - i < n) {
                    ret.append(s.charAt(j + cycleLen - i));
                }
            }
        }
        return ret.toString();
    }

    public static void main(String[] args) {
        String s = "LEETCODEISHIRING";
        System.out.println(convert(s,4));
    }

}
