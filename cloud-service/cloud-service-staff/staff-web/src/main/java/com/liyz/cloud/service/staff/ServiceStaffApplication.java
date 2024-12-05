package com.liyz.cloud.service.staff;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/30 11:04
 */
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = {"com.liyz.cloud.service.staff.dao"})
@SpringBootApplication(exclude = {EurekaClientAutoConfiguration.class, EurekaDiscoveryClientConfiguration.class})
public class ServiceStaffApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceStaffApplication.class, args);
    }
}