package com.thunisoft.compareTask;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Description:
 * Datetime:    2021/1/9   17:20
 * Author:   zhangliujie
 */
@Deprecated
public class MySchedulerFactory extends SchedulerFactoryBean {

    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    private JobBeanFactory jobBeanFactory;

    public MySchedulerFactory() {
        super.setJobFactory(jobBeanFactory);
        super.setApplicationContextSchedulerContextKey("applicationContextKey");
    }
}
