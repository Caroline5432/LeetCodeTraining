package com.thunisoft.compareTask;

/**
 * Description:
 * Datetime:    2021/1/9   16:19
 * Author:   zhangliujie
 */
public final class CodeConsts {
    private CodeConsts(){
    }

    /**
     * 等待
     */
    public static final int CODETYPE_TASK_STATUS_WAIT = 0;

    public static final String TASK_STATUS_WAIT = "等待";

    /**
     * 运行中
     */
    public static final int CODETYPE_TASK_STATUS_RUNNING = 1;

    public static final String TASK_STATUS_RUNNING = "运行中";
    /**
     * 运行成功
     */
    public static final int CODETYPE_TASK_STATUS_SUCCESS = 2;

    public static final String TASK_STATUS_SUCCESS = "成功";
    /**
     * 运行失败
     */
    public static final int CODETYPE_TASK_STATUS_FAILED = 3;

    public static final String TASK_STATUS_FAILED = "失败";
}
