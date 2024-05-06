package com.liyz.cloud.common.api.config;

import com.liyz.cloud.common.api.error.ErrorApiController;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/5/6 17:33
 */
@Configuration
@EnableConfigurationProperties({ServerProperties.class})
public class CloudApiAutoConfig {

    @Bean
    public ErrorApiController errorApiController(ServerProperties serverProperties) {
        return new ErrorApiController(serverProperties);
    }
}
