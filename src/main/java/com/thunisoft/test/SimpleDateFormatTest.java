package com.thunisoft.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/12/12.
 */
public class SimpleDateFormatTest extends Thread {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String name;

    private String dateStr;

    public SimpleDateFormatTest(String name, String dateStr) {
        this.name = name;
        this.dateStr = dateStr;
    }

    @Override
    public void run() {
        try {
            Date date = sdf.parse(dateStr);
            System.out.println(name + ":date:" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new SimpleDateFormatTest("A", "2019-11-11"));
        executorService.execute(new SimpleDateFormatTest("B", "2019-12-12"));

        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse("2019-12-12");
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/


    }

}
