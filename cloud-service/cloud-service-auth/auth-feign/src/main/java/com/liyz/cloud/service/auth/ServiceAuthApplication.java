package com.liyz.cloud.service.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Desc:启动类
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/5/7 15:10
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.liyz.cloud.service.auth.feign", "com.liyz.cloud.common.api.feign"})
@MapperScan(basePackages = {"com.liyz.cloud.service.auth.dao"})
@SpringBootApplication
public class ServiceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class, args);
    }
}