package com.liyz.cloud.common.task.annotation;

import com.liyz.cloud.common.task.constant.TaskConstant;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/11 10:57
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface ElasticSimpleJob {

    String cron() default "";

    int shardingTotalCount() default 1;

    String shardingItemParameters() default "";

    String jobParameter() default "";

    String dataSource() default "";

    String description() default "";

    boolean disabled() default false;

    boolean overwrite() default true;

    int monitorPort() default -1;

    String jobExceptionHandler() default TaskConstant.JobExceptionHandler;

    String executorServiceHandler() default TaskConstant.ExecutorServiceHandler;
}
