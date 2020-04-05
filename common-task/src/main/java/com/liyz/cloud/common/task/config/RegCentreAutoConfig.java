package com.liyz.cloud.common.task.config;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.liyz.cloud.common.task.annotation.EnableElasticJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 12:30
 */
@Configuration
@ConditionalOnClass(ZookeeperRegistryCenter.class)
@ConditionalOnBean(annotation = EnableElasticJob.class)
@EnableConfigurationProperties(RegCenterProperties.class)
public class RegCentreAutoConfig {

    @Autowired
    RegCenterProperties regCenterProperties;

    @Bean(initMethod = "init")
    public CoordinatorRegistryCenter registryCenter() {
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(regCenterProperties.getServerLists(),
                regCenterProperties.getNamespace());
        zkConfig.setBaseSleepTimeMilliseconds(regCenterProperties.getBaseSleepTimeMilliseconds());
        zkConfig.setConnectionTimeoutMilliseconds(regCenterProperties.getConnectionTimeoutMilliseconds());
        zkConfig.setDigest(regCenterProperties.getDigest());
        zkConfig.setMaxRetries(regCenterProperties.getMaxRetries());
        zkConfig.setMaxSleepTimeMilliseconds(regCenterProperties.getMaxSleepTimeMilliseconds());
        zkConfig.setSessionTimeoutMilliseconds(regCenterProperties.getSessionTimeoutMilliseconds());
        return new ZookeeperRegistryCenter(zkConfig);
    }
}
