package com.liyz.cloud.service.task.handler;

import com.alibaba.fastjson.JSONArray;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.google.common.collect.Lists;
import com.liyz.cloud.common.task.annotation.JobScheduled;
import com.liyz.cloud.common.task.constant.TaskConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 12:59
 */
@Slf4j
@JobScheduled(jobName = "测试", cron = "*/10 * * * * ?", description = "测试", shardingTotalCount = 3,
        shardingItemParameters = "0=1,1=2,2=3", dataSource = TaskConstant.DEFAULT_DATASOURCE)
public class TestTask implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        List<String> list = Lists.newArrayList("123", "2345");
        try {
            log.info("list:{}", CollectionUtils.isEmpty(list) ? "null" : JSONArray.toJSONString(list));
            log.info("shardingItem:{}", shardingContext.getShardingItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
