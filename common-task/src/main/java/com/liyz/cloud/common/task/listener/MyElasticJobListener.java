package com.liyz.cloud.common.task.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 12:26
 */
@Slf4j
public class MyElasticJobListener implements ElasticJobListener {

    private long beginTime = 0;
    /**
     * 作业执行前的执行的方法
     *
     * @param shardingContexts
     */
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        beginTime = System.currentTimeMillis();
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
        log.info("********jobName:{}, itemParameters:{}, parameter:{}----执行完成了, 耗时:{}ms************",
                shardingContexts.getJobName(), shardingContexts.getShardingItemParameters(),
                shardingContexts.getJobParameter(), System.currentTimeMillis() - beginTime);
    }
}
