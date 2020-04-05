package com.liyz.cloud.service.task;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.liyz.cloud.common.task.annotation.EnableElasticJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 12:52
 */
@EnableElasticJob
@EnableApolloConfig
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = {"com.liyz.cloud.service.task.dao"})
@SpringBootApplication(scanBasePackages = {"com.liyz.cloud"})
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}
