package com.liyz.cloud.service.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/11 16:37
 */
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@MapperScan(basePackages = {"com.liyz.cloud.service.sms.dao"})
@SpringBootApplication(scanBasePackages = {"com.liyz.cloud"})
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }
}