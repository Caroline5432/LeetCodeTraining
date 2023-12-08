package com.training.compareTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.training.test.ScheduledCompareTask;

/**
 * Description:
 * Datetime:    2021/1/9   15:28
 * Author:   zhangliujie
 */
@Component
public class TaskQuartzManager {
    private static final Logger log = LoggerFactory.getLogger(TaskQuartzManager.class);

    /**
     * 已经启动的定时任务
     */
    private static Map<String, ScheduledCompareTask> scheduledStore = new ConcurrentHashMap<String, ScheduledCompareTask>();

    /**
     * QUARTZCTASK
     */
    private static final String QUARTZCTASK = "quartz_cron_task";

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    /**
     * 初始化，从数据库中获取
     */
    @PostConstruct
    public void init() {
        log.info("开始初始化定时任务...");
        jobInit();
    }

    /**
     * 定时任务初始化
     */
    private void jobInit() {
        try {
            // 查询有效的分组
            List<CompareTask> taskList = new ArrayList<>();
            CompareTask task1 = new CompareTask();
            task1.setCId("111111");
            task1.setCMc("比对任务1");
            task1.setNYx(1);
            task1.setCCron("*/1 * * * * ?");
            task1.setCronMs("每一分钟执行一次");
            CompareTask task2 = new CompareTask();
            task2.setCId("222222");
            task2.setCMc("比对任务2");
            task2.setNYx(1);
            task2.setCCron("0 35 17 * * ?");
            task2.setCronMs("每天下午五点执行一次");
            // taskList.add(task1);
            taskList.add(task2);
            // 提交定时任务
            for (CompareTask task : taskList) {
                taskJobInit(task);
            }
            // 添加流程控制相关的定时任务
            //addDelProcessJob();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
            log.info("初始化定时任务结束，初始化【{}】个任务。", scheduledStore.size());
        } catch (Exception e) {
            log.error("初始化定时任务异常", e);
        }
    }

    /**
     * 校验task是否有效
     *
     * @param task
     */
    public void taskJobInit(CompareTask task) {
        try {
            if (validExpress(task.getCCron())) {
                addJob(task);
            } else {
                log.warn("不合法的定时表达式，taskname：{}，cron:{}", task.getCMc(),
                        task.getCCron());
            }
        } catch (Exception e) {
            log.warn("添加比对定时任务异常，{}", e.getMessage());
        }
    }

    private boolean validExpress(String cron) {
        if (StringUtils.isBlank(cron)) {
            return false;
        }
        return CronExpression.isValidExpression(cron);
    }

    /**
     * 增加任务
     *
     * @param task
     * @throws TaskScheduledException
     */
    public synchronized void addJob(CompareTask task) throws TaskScheduledException {
        if (null == task) {
            log.warn("任务不能为空");
            return;
        }
        if (StringUtils.isEmpty(task.getCCron())) {
            log.warn("任务定时参数不能为空");
            return;
        }
        JobKey jobKey = JobKey.jobKey(task.getCId(), QUARTZCTASK);
        TriggerKey triggerKey = TriggerKey.triggerKey(task.getCId(), QUARTZCTASK);
        JobDetail jobDetail = JobBuilder.newJob(TaskJobExecutor.class).withIdentity(jobKey).build();
        jobDetail.getJobDataMap().put("task", task);
        jobDetail.getJobDataMap().put("taskQuartzManager", this);
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCCron());
        cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();// 忽略错过的任务
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                .withSchedule(cronScheduleBuilder).build();
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            // 添加缓存
            addScheduledTaskCache(task, cronTrigger.getNextFireTime());
            log.info("分组任务【{}】添加成功", task.getCMc());
        } catch (Exception e) {
            log.error("分组任务【{}】添加失败", task.getCMc());
            throw new TaskScheduledException(e);
        }
    }

    /**
     * 添加任务缓存
     *
     * @param task task
     * @param nextFireTime nextTime
     */
    private void addScheduledTaskCache(CompareTask task, Date nextFireTime) {
        String key = task.getCId();
        ScheduledCompareTask cache = new ScheduledCompareTask();
        cache.setId(key);
        cache.setNextFireTime(nextFireTime);
        cache.setName(task.getCMc());
        scheduledStore.put(key, cache);
    }

    /**
     * 更新任务的下次计划时间
     *
     * @param task
     * @param nextFireTime  nextFireTime
     */
    public void updateScheduledTaskNextTime(CompareTask task, Date nextFireTime) {
        String key = task.getCId();
        ScheduledCompareTask cache = scheduledStore.get(key);
        cache.setNextFireTime(nextFireTime);
    }

}
