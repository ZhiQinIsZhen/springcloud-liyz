package com.liyz.cloud.common.task.core;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.executor.handler.JobProperties;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.liyz.cloud.common.task.annotation.ElasticDataFlowJob;
import com.liyz.cloud.common.task.annotation.ElasticSimpleJob;
import com.qjdchina.ai.common.base.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

/**
 * 注释: job任务加载核心类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/11 11:10
 */
@Slf4j
@Configuration
@ConditionalOnExpression("'${elastic.job.zookeeper.server-lists}'.length() > 0")
public class ElasticJobAutoLoad {

    @Autowired
    ZookeeperRegistryCenter registryCenter;
    @Autowired
    SpringContextUtil springContextUtil;

    @PostConstruct
    public void initElasticJob() throws Exception {
        Map<String, SimpleJob> simpleJobMap = SpringContextUtil.getBeansOfType(SimpleJob.class);
        Map<String, DataflowJob> flowJobMap = SpringContextUtil.getBeansOfType(DataflowJob.class);
        log.info("扫描到 simple job 数量:{}", simpleJobMap.size());
        log.info("扫描到 flow job 数量:{}", flowJobMap.size());
        loadSimpleJob(simpleJobMap);
        loadFlowJob(flowJobMap);
    }

    private void loadSimpleJob(Map<String, SimpleJob> simpleJobMap) {
        for (Map.Entry<String, SimpleJob> entry : simpleJobMap.entrySet()) {
            SimpleJob simpleJob = entry.getValue();
            ElasticSimpleJob elasticSimpleJob = simpleJob.getClass().getAnnotation(ElasticSimpleJob.class);
            SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration
                (
                    JobCoreConfiguration.newBuilder(entry.getKey(), elasticSimpleJob.cron(), elasticSimpleJob.shardingTotalCount())
                            .jobParameter(elasticSimpleJob.jobParameter())
                            .description(elasticSimpleJob.description())
                            .jobProperties(JobProperties.JobPropertiesEnum.JOB_EXCEPTION_HANDLER.getKey(), elasticSimpleJob.jobExceptionHandler())
                            .jobProperties(JobProperties.JobPropertiesEnum.EXECUTOR_SERVICE_HANDLER.getKey(), elasticSimpleJob.executorServiceHandler())
                            .build(),
                    simpleJob.getClass().getCanonicalName()
                );
            LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration)
                    .overwrite(elasticSimpleJob.overwrite())
                    .monitorPort(elasticSimpleJob.monitorPort())
                    .build();
            String dataSource = elasticSimpleJob.dataSource();
            doLoadElasticJob(dataSource, liteJobConfiguration, simpleJob);
        }
    }

    private void loadFlowJob(Map<String, DataflowJob> flowJobMap) {
        for (Map.Entry<String, DataflowJob> entry : flowJobMap.entrySet()) {
            DataflowJob dataflowJob = entry.getValue();
            ElasticDataFlowJob elasticDataFlowJob = dataflowJob.getClass().getAnnotation(ElasticDataFlowJob.class);
            DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration
                    (
                            JobCoreConfiguration.newBuilder(entry.getKey(), elasticDataFlowJob.cron(), elasticDataFlowJob.shardingTotalCount())
                                    .jobParameter(elasticDataFlowJob.jobParameter())
                                    .description(elasticDataFlowJob.description())
                                    .jobProperties(JobProperties.JobPropertiesEnum.JOB_EXCEPTION_HANDLER.getKey(), elasticDataFlowJob.jobExceptionHandler())
                                    .jobProperties(JobProperties.JobPropertiesEnum.EXECUTOR_SERVICE_HANDLER.getKey(), elasticDataFlowJob.executorServiceHandler())
                                    .build(),
                            dataflowJob.getClass().getCanonicalName(),
                            elasticDataFlowJob.streamingProcess()
                    );
            LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(dataflowJobConfiguration)
                    .overwrite(elasticDataFlowJob.overwrite())
                    .monitorPort(elasticDataFlowJob.monitorPort())
                    .build();
            String dataSource = elasticDataFlowJob.dataSource();
            doLoadElasticJob(dataSource, liteJobConfiguration, dataflowJob);
        }
    }

    private void doLoadElasticJob(String dataSource, LiteJobConfiguration liteJobConfiguration, ElasticJob elasticJob) {
        JobEventRdbConfiguration rdbConfiguration = checkDataSourceExist(dataSource);
        SpringJobScheduler jobScheduler;
        if (Objects.isNull(rdbConfiguration)) {
            jobScheduler = new SpringJobScheduler
                    (
                            elasticJob,
                            registryCenter,
                            liteJobConfiguration,
                            new MyElasticJobListener()
                    );
        } else {
            jobScheduler = new SpringJobScheduler
                    (
                            elasticJob,
                            registryCenter,
                            liteJobConfiguration,
                            rdbConfiguration,
                            new MyElasticJobListener()
                    );
        }
        jobScheduler.init();
    }

    private JobEventRdbConfiguration checkDataSourceExist(String dataSourceName) {
        if (StringUtils.isNotBlank(dataSourceName)) {
            if (!SpringContextUtil.containsBean(dataSourceName)) {
                throw new RuntimeException("not exist datasource ["+dataSourceName+"] !");
            }
            DataSource dataSource = SpringContextUtil.getBean(dataSourceName, DataSource.class);
            return new JobEventRdbConfiguration(dataSource);
        }
        return null;
    }
}
