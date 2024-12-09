package com.liyz.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Desc:启动类
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/15 10:51
 */
@EnableFeignClients(basePackages = {"com.liyz.cloud.service.*.feign"})
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}