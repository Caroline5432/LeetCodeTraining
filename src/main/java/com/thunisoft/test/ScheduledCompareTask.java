package com.thunisoft.test;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 * Datetime:    2021/1/9   15:29
 * Author:   zhangliujie
 */
@Data
public class ScheduledCompareTask implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //任务主键
    private String id;

    private Date nextFireTime;

    private String name;

}
