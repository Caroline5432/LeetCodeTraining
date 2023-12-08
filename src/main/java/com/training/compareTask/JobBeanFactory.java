/*
 * @(#)TaskJobBeanFactory.java 2017-8-23上午10:12:46 sjcjgj Copyright 2017
 * Thuisoft, Inc. All rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL. Use
 * is subject to license terms.
 */
package com.training.compareTask;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * 任务bean工厂
 * TaskJobBeanFactory
 * @author zhangshiquan
 * @time 2017-8-23上午10:12:46
 * @version 1.0
 *
 */
@Deprecated
public class JobBeanFactory extends AdaptableJobFactory{

    /**
     * spring 自动装配
     */

    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    public JobBeanFactory() {

    }


    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        //调用父类工厂创建
        Object jobObj = super.createJobInstance(bundle);
        //自动注入
        autowireCapableBeanFactory.autowireBean(jobObj);
        return jobObj;
    }

}
