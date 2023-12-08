package com.training.compareTask;


import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * Description: 定时任务执行器
 * Datetime:    2021/1/9   15:52
 * Author:   zhangliujie
 */
@Component
public class TaskJobExecutor extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(TaskJobExecutor.class);

    @Override
    public void executeInternal(JobExecutionContext context) {
        CompareTask task = (CompareTask) context.getJobDetail().getJobDataMap().get("task");
        TaskQuartzManager taskQuartzManager = (TaskQuartzManager) context.getJobDetail().getJobDataMap().get("taskQuartzManager");
        try {
            Date nextFireTime = context.getTrigger().getNextFireTime();
            // 更新下次执行时间
            taskQuartzManager.updateScheduledTaskNextTime(task, nextFireTime);
            // todo 任务执行器开始比对任务
            log.info("比对任务{}开始执行", task.getCMc());
        } catch (ExecTaskException e) {
            log.error("任务执行失败，name：{}，message：{}", task.getCMc(), e.getMessage());
        } catch (Exception e) {
            log.error("定时触发的任务[" + task.getCMc() + "]定时启动异常！", e);
        }
    }

    /**
     * 获取当前时间
     * 返回的时间没有毫秒值
     *
     * @return
     */
    public static Date getCurDateTime() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.MILLISECOND, 0);
        return today.getTime();
    }
}
