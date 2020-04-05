package com.liyz.cloud.common.task.constant;

/**
 * 注释: task常量
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/11 10:59
 */
public interface TaskConstant {

    String Default_JobExceptionHandler = "com.dangdang.ddframe.job.executor.handler.impl.DefaultJobExceptionHandler";

    String Default_ExecutorServiceHandler = "com.dangdang.ddframe.job.executor.handler.impl.DefaultExecutorServiceHandler";

    String JobExceptionHandler = "com.liyz.cloud.common.task.listener.MyJobExceptionHandler";

    String DEFAULT_DATASOURCE = "dataSource";

    String PREFIX = "elasticjob";
}
