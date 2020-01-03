package com.liyz.cloud.common.task.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 注释: 线程池配置类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/11 11:07
 */
@Slf4j
//@Configuration
//@EnableAsync
public class TaskExecutePool {

//    @Bean
    public TaskExecutor initExecutorService() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(2);
        // 设置最大线程数
        executor.setMaxPoolSize(50);
        // 设置队列容量
        executor.setQueueCapacity(100);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("elastic-task-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
