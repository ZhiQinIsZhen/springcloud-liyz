package com.liyz.cloud.common.task.core;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 注释:作业执行监听器
 * 有必要的话，可以自己放入数据库或者一个统计的地方，看业务需要
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/11 14:49
 */
@Slf4j
public class MyElasticJobListener implements ElasticJobListener {

    /**
     * 作业执行前的执行的方法
     *
     * @param shardingContexts
     */
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        log.info("********jobName:{}, itemParameters:{}, parameter:{}----开始执行了************",
                shardingContexts.getJobName(), shardingContexts.getShardingItemParameters(),
                shardingContexts.getJobParameter());
    }

    /**
     * 作业执行后的执行的方法
     *
     * @param shardingContexts
     */
    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        log.info("********jobName:{}, itemParameters:{}, parameter:{}----执行完成了************",
                shardingContexts.getJobName(), shardingContexts.getShardingItemParameters(),
                shardingContexts.getJobParameter());
    }
}
