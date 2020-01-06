package com.thunisoft.test;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/12/4.
 */
public class QuartzTest {

    public static void main(String[] args) {
        String timeSwitch = "true";
        String startTime = "2019-01-01";
        String intervalTime = "2000";
        if (StringUtils.equals("true", timeSwitch) && StringUtils.isNoneBlank(startTime) &&
                StringUtils.isNoneBlank(intervalTime)) {
/*                this.interfaceTimerValid=true;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate = null;
                try {
                    startDate = sdf.parse(startTime);//起始时间
                } catch (ParseException e) {
                    logger.error(e.getMessage(), e);
                }*/
            Calendar calendar = Calendar.getInstance();
            //calendar.setTime(startDate);

            //calendar.add(Calendar.DAY_OF_MONTH, 1);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
            System.out.println(format.format(calendar.getTime()));

            long timeInterval = Long.parseLong(intervalTime);
            Timer t = new Timer();

            //定时任务
            t.schedule(new TimerTask() {
                public void run() {
                    // 定时器主要执行的代码块
                    System.out.println("定时任务启动");
                    //logger.info("定时任务启动");
                    //obtainInterfaceTask(ds, path,interfaceTimerValid);
                }
            }, calendar.getTime(), timeInterval);
        }
    }

}
