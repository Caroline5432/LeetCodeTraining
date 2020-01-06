package com.thunisoft.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/11/22.
 */
public class DateTest {

    public static void main(String[] args) {
        String reportDate = "2019-11-20 00:00:00";
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd HH:mm");
        try {
            reportDate = sf2.format(sf1.parse(reportDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(reportDate);
        String s = "31020";
        System.out.println(s.substring(s.length()-4));

        String wjbs = "s3.dzjz-1435-2:writ-server/writ/2019/11/14/民事判决书（侵害消费者权益公益诉讼用）/81317e71d5444dc2bf86b93dc50ef248.docx";
        System.out.println(wjbs.substring(wjbs.lastIndexOf("/") + 1));

        System.out.println(System.currentTimeMillis());

        try {
            System.out.println(sf1.parse(reportDate).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println((sf1.format(new Date(-28800000))));
        Date date = new Date(-28800000);

        System.out.println(new Date().getTime());
        System.out.println((sf1.format(new Date(Long.valueOf("1574232748599")))));

        String xml = "24010C6A62CB96D38A46C94C369FCB2E50DB";

        System.out.println(xml.length());

        System.out.println(new Date().getTime());
    }

}
