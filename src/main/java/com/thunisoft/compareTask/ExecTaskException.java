package com.thunisoft.compareTask;

/**
 * Description: 触发执行任务异常
 * Datetime:    2021/1/9   16:03
 * Author:   zhangliujie
 */
public class ExecTaskException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ExecTaskException() {
        super();
    }

    public ExecTaskException(String message) {
        super(message);
    }

    public ExecTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
