package com.liyz.cloud.common.controller.config;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/12 17:58
 */
@Configuration
public class FeignConfig {

    @Bean
    public Request.Options options() {
        return new Request.Options(5000, 10000);
    }
}
