package com.liyz.cloud.api.staff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Desc:启动类
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/30 9:25
 */

@EnableFeignClients(basePackages = {"com.liyz.cloud.api.staff.feign", "com.liyz.cloud.common.api.feign"})
@SpringBootApplication
public class ApiStaffApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiStaffApplication.class, args);
    }
}