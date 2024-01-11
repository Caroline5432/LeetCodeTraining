package com.training.algorithm.test;

/**
 * @Description:十进制转二进制
 */
public class DecimalToBinary {

    /**
     * 递归将十进制转为二进制
     * @param decimal 十进制数值
     * @return 二进制数值
     */
    public static String convertByRecursive (int decimal) {
        if (decimal == 0) {
            return "0";
        } else if (decimal == 1) {
            return "1";
        } else {
            return convertByRecursive(decimal / 2) + (decimal % 2);
        }
    }


    /**
     * 循环将十进制转为二进制
     * @param decimal 十进制数值
     * @return 二进制数值
     */
    public static String convertByLoop (int decimal) {
        StringBuilder binary = new StringBuilder();
        while (decimal > 0) {
            binary.insert(0, decimal % 2);
            decimal = decimal / 2;
        }
        return binary.toString();
    }

    /**
     * 位操作将十进制转为二进制
     * @param decimal 十进制数值
     * @return 二进制数值
     */
    public static String convertByBitManipulation (int decimal) {
        if (decimal == 0) {
            return "0";
        }

        StringBuilder binary = new StringBuilder();
        while (decimal > 0) {
            binary.insert(0, (decimal & 1));
            decimal >>= 1;
        }
        return binary.toString();
    }

    public static void main(String[] args) {
        int decimal = 25;
        System.out.println("convertByRecursive : " + convertByRecursive(decimal));
        System.out.println("convertByLoop : " + convertByLoop(decimal));
        System.out.println("convertByBitManipulation : " + convertByBitManipulation(decimal));

    }

}
