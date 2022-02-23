package com.thunisoft.compareTask;

/**
 * Description:定时任务调用时传参异常时的检测异常
 * Datetime:    2021/1/9   15:49
 * Author:   zhangliujie
 */
public class TaskScheduledException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -4341065155344098149L;

    /**
     *
     */
    public TaskScheduledException() {
        super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public TaskScheduledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public TaskScheduledException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public TaskScheduledException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public TaskScheduledException(Throwable cause) {
        super(cause);
    }
}
