package com.liyz.cloud.common.task.core;

import com.dangdang.ddframe.job.executor.handler.JobExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 注释:作业异常处理类
 * 有必要也可以放入数据库，做统计
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/11 15:06
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
        log.error("【{}】:任务异常", s, throwable);
    }
}
