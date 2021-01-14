package com.thunisoft.test;

/**
 * Description:
 * Datetime:    2021/1/5   15:02
 * Author:   zhangliujie
 */
public class StringTest {

    public static void main(String[] args) {
        String filename = "DA_11_1862000_1.xml";
        int indexOfPoint = filename.indexOf(".");
        String dahjz = filename.substring(indexOfPoint-1, indexOfPoint);
        System.out.println(dahjz);
    }

    private static void test() {
        String a = "1";
        System.out.println("1".equals(String.valueOf(a)));

        Object[] objs = new Object[2];
        objs[0] = null;
        System.out.println("1".equals(String.valueOf(objs[0])));

    }

}
