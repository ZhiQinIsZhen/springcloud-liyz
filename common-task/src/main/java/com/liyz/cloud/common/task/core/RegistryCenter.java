package com.liyz.cloud.common.task.core;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注释: 注册中心
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/11 15:36
 */
@Configuration
@ConditionalOnExpression("'${elastic.job.zookeeper.server-lists}'.length() > 0")
public class RegistryCenter {

    @Bean
    public ZookeeperRegistryCenter intiRegistryCenter(@Value("${elastic.job.zookeeper.server-lists}") final String serverList,
                                                      @Value("${elastic.job.zookeeper.namespace}") final String namespace) {
        ZookeeperRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
        regCenter.init();
        return regCenter;
    }
}
