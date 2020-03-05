package com.liyz.cloud.service.push;

import com.liyz.cloud.service.push.strap.BootStrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/5 9:32
 */
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.liyz"})
public class PushApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(PushApplication.class);
        ConfigurableApplicationContext context = application.run(args);

        BootStrap bootStrap = context.getBean(BootStrap.class);
        new Thread(bootStrap).start();
    }
}
