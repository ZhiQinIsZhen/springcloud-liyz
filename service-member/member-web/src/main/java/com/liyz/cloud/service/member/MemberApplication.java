package com.liyz.cloud.service.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/3 17:40
 */
@EnableDiscoveryClient
@EnableScheduling
@MapperScan(basePackages = {"com.liyz.cloud.service.member.dao"})
@SpringBootApplication(scanBasePackages = {"com.liyz.cloud.common", "com.liyz.cloud.service.member"})
public class MemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}
