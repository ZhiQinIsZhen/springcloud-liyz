package com.liyz.cloud.common.xxl.job;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/5/15 10:51
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(XxlJobProperties.class)
@ConditionalOnProperty(prefix = "xxl.job", name = "enable", havingValue = "true")
public class CloudCommonXxlJobAutoConfig {

    @Bean
    @ConditionalOnMissingBean(XxlJobSpringExecutor.class)
    public XxlJobSpringExecutor xxlJobExecutor(XxlJobProperties properties) {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(properties.getAdminAddress());
        xxlJobSpringExecutor.setAppname(properties.getAppName());
        xxlJobSpringExecutor.setPort(properties.getPort());
        xxlJobSpringExecutor.setAccessToken(properties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(properties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(properties.getLogRetentionDays());
        log.info("xxl job executor start.");
        return xxlJobSpringExecutor;
    }
}
