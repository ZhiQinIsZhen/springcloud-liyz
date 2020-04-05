package com.liyz.cloud.common.task.listener;

import com.dangdang.ddframe.job.executor.handler.JobExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 12:28
 */
@Slf4j
public class MyJobExceptionHandler implements JobExceptionHandler {

    /**
     * 处理作业异常
     *
     * @param s
     * @param throwable
     */
    @Override
    public void handleException(String s, Throwable throwable) {
        log.error("【jobName:{}】:任务异常", s, throwable);
    }
}
