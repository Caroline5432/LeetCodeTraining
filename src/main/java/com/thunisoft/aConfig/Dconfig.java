package com.thunisoft.aConfig;

import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.thunisoft.compareTask.JobBeanFactory;

/**
 * Description:
 * Datetime:    2021/1/11   18:12
 * Author:   zhangliujie
 */
@Deprecated
public class Dconfig {
    @Bean
    public AutowireCapableBeanFactory autowireCapableBeanFactory(){
        return   new DefaultListableBeanFactory();
    }

    @Bean
    public JobFactory jobBeanFactory(AutowireCapableBeanFactory autowireCapableBeanFactory){
        JobFactory adaptableJobFactory = new AdaptableJobFactory(){
            @Override
            protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
                //调用父类工厂创建
                Object jobObj = super.createJobInstance(bundle);
                //自动注入
                autowireCapableBeanFactory.autowireBean(jobObj);
                return jobObj;
            }
        };
        return adaptableJobFactory;
    }
    @Bean
    public SchedulerFactoryBean schedulerFactory(AutowireCapableBeanFactory autowireCapableBeanFactory,JobBeanFactory jobBeanFactory){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobBeanFactory);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
        return schedulerFactoryBean;
    }


}
