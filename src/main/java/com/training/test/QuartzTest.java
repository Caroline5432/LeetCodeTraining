package com.training.test;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/12/4.
 */
public class QuartzTest {

    //private boolean interfaceTimerValid = false;

    public static void main(String[] args) {
        runTest();
    }

    private static void runTest() {
        String timeSwitch = "true";
        String startTime = "2020-02-25 15:49:00";
        String intervalTime = "2000";
        //定时任务
        if (StringUtils.equals("true", timeSwitch) && StringUtils.isNoneBlank(startTime) &&
                StringUtils.isNoneBlank(intervalTime)) {
            //interfaceTimerValid=true;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = null;
            try {
                startDate = sdf.parse(startTime);
            } catch (ParseException e) {
                System.out.println(e);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            long timeInterval = Long.parseLong(intervalTime);

            Timer t = new Timer();
            t.schedule(new TimerTask() {
                public void run() {
                    // 定时器主要执行的代码块
                    //obtainInterfaceTask(ds, path,interfaceTimerValid);
                    System.out.println(new Date() + "定时任务执行");
                }
            }, calendar.getTime(), timeInterval);
        }
    }

    private static void runTest2() {
        String timeSwitch = "true";
        String startTime = "2020-02-25 15:49:00";
        String intervalTime = "2000";
        String firstTime = "";
        if (StringUtils.equals("true", timeSwitch) && StringUtils.isNoneBlank(startTime) &&
                StringUtils.isNoneBlank(intervalTime)) {
            //this.interfaceTimerValid=true;
/*                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate = null;
                try {
                    startDate = sdf.parse(startTime);//起始时间
                } catch (ParseException e) {
                    logger.error(e.getMessage(), e);
                }*/
            Date d = new Date();
            System.out.println(d);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateNowStr = sdf.format(d);
            dateNowStr=dateNowStr+" "+firstTime;
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date today1 = null;
            try {
                today1 = sdf1.parse(dateNowStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("定时任务启动时间-------------------------"+today1);
            Calendar calendar = Calendar.getInstance();
            //calendar.setTime(startDate);
            //calendar.add(Calendar.DAY_OF_MONTH, 1);
            long timeInterval = Long.parseLong(intervalTime);
            Timer t = new Timer();
            //定时任务
            t.schedule(new TimerTask() {
                public void run() {
                    // 定时器主要执行的代码块
                    System.out.println(new Date() + "定时任务执行");
                }
            }, today1, timeInterval);
        }
    }

}
