package com.liyz.cloud.service.file;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
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
 * @date 2020/3/16 16:08
 */
@EnableApolloConfig
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@MapperScan(basePackages = {"com.liyz.cloud.service.file.dao"})
@SpringBootApplication(scanBasePackages = {"com.liyz.cloud"})
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
