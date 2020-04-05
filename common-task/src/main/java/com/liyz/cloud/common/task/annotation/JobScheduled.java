package com.liyz.cloud.common.task.annotation;

import com.liyz.cloud.common.task.constant.TaskConstant;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 12:23
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
@Inherited
public @interface JobScheduled {

    String jobName();

    String cron() default "";

    int shardingTotalCount() default 1;

    String shardingItemParameters() default "";

    String jobParameter() default "";

    String dataSource() default "";

    String description() default "";

    boolean failover() default false;

    boolean misfire() default false;

    boolean disabled() default false;

    boolean overwrite() default true;

    int monitorPort() default -1;

    String scriptCommandLine() default "";

    int maxTimeDiffSeconds() default -1;

    boolean monitorExecution() default true;

    boolean streamingProcess() default false;

    String jobExceptionHandler() default TaskConstant.JobExceptionHandler;

    String executorServiceHandler() default TaskConstant.Default_ExecutorServiceHandler;

    String jobShardingStrategyClass() default "";
}
