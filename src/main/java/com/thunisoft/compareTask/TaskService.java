package com.thunisoft.compareTask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Datetime:    2021/1/11   15:49
 * Author:   zhangliujie
 */
@Slf4j
@Service
public class TaskService {

    public void exeTask(CompareTask task) {
        log.info("比对任务{}开始执行", task.getCMc());
    }

}
