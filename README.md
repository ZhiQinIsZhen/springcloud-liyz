# Spring Cloud Project By Jdk 21

[![Build Status](https://img.shields.io/badge/Build-ZhiQinlsZhen-red)](https://github.com/ZhiQinIsZhen)
[![License](https://img.shields.io/badge/License-MIT-yellow)](https://github.com/ZhiQinIsZhen/springcloud-liyz/blob/master/LICENSE)
![JDK Version](https://img.shields.io/badge/JDK-21-brightgreen)
![Springboot Version](https://img.shields.io/badge/Springboot-3.3.4-brightgreen)
[![Spring Cloud](https://img.shields.io/badge/Springcloud-2023.0.3-brightgreen)](https://spring.io/projects/spring-cloud)
![Gateway Version](https://img.shields.io/badge/Gateway-4.1.3-brightgreen)
![jjwt Version](https://img.shields.io/badge/jjwt-0.12.6-brightgreen)
![Mybatis-plus Version](https://img.shields.io/badge/MybatisPlus-3.5.8-brightgreen)
![Swagger Version](https://img.shields.io/badge/knife4j-4.5.0-brightgreen)
![Elasticjob Version](https://img.shields.io/badge/elasticjob-3.0.4-brightgreen)

这是一个Spring Cloud项目，基于SpringBoot、SpringCloud、Mybatis-plus等框架。

- 主框架基于：Spring Cloud、Spring Boot、Mybatis-plus、knife4j
- 注册中心基于：Eureka
- 监控中心基于：Spring Admin
- 登陆安全基于：Spring Security、jjwt、redisson
- 接口文档基于：Swagger-knife4j
- 限流基于：Google-guava
- 分布式定时任务基于：Elastic-job
- 推送聊天基于：Netty
- 分库分表读写分离基于：Sharding-jdbc


#### 核心依赖 

依赖 | 版本
--- | ---
Spring Boot |   3.2.5
Spring security | 3.2.5
Spring Cloud |  2023.0.1
Mybatis-plus | 3.5.6
Redisson |  3.29.0
Elastic Job | 3.0.4
Swagger Knife4j | 4.4.0


### 目录结构说明
```lua
1. `cloud-dependencies-bom`：Maven Pom版本管理文件
2. `cloud-gateway`：前置网关层，集成限流与JWT验证
3. `cloud-api`：后置网关层，即真正的入口
4. `cloud-common`：基础包的框架
5. `cloud-service`：Feign的服务提供者，即业务服务
```

#### 开源共建
1.如有问题可以提交[issue](https://github.com/ZhiQinIsZhen/springcloud-liyz/issues)

2.如有需要Dubbo项目，请点击[Spring Boot + Dubbo](https://github.com/ZhiQinIsZhen/dubbo-springboot-project)