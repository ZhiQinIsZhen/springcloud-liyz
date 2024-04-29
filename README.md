# Spring Cloud Liyz Project

[![Build Status](https://img.shields.io/badge/Build-ZhiQinlsZhen-red)](https://github.com/ZhiQinIsZhen)
[![License](https://img.shields.io/badge/License-MIT-yellow)](https://github.com/ZhiQinIsZhen/springcloud-liyz/blob/master/LICENSE)
![Springboot Version](https://img.shields.io/badge/Springboot-3.2.5-brightgreen)
[![Spring Cloud](https://img.shields.io/badge/Springcloud-2023.0.1-brightgreen)](https://spring.io/projects/spring-cloud)
![Gateway Version](https://img.shields.io/badge/Gateway-4.1.3-brightgreen)
![jjwt Version](https://img.shields.io/badge/jjwt-0.12.5-brightgreen)
![Mybatis-plus Version](https://img.shields.io/badge/MybatisPlus-3.5.6-brightgreen)
![Swagger Version](https://img.shields.io/badge/knife4j-4.4.0-brightgreen)
![Elasticjob Version](https://img.shields.io/badge/elasticjob-3.0.4-brightgreen)

这是一个Spring Cloud项目，基于SpringBoot、Mybatis等框架。

- 主框架基于：Spring Cloud、Spring Boot、Mybatis、ElasticSearch、Kafka
- 注册中心基于：Eureka
- 监控中心基于：Spring Admin
- 登陆安全基于：Spring Security oauth2、jwt、redisson
- 接口文档基于：Swagger-knife4j
- 限流基于：Google-guava
- 分布式定时任务基于：Elastic-job（注解式）
- 推送聊天基于：Netty
- 分库分表读写分离基于：Sharding-jdbc


#### 核心依赖 

依赖 | 版本
--- | ---
Spring Boot |   2.2.7.RELEASE
Spring security | 2.2.7.RELEASE
Spring Cloud |  Hoxton.SR3
Mybatis | 2.1.2
Zookeeper Curator | 4.3.0
Redisson |  3.13.0
Elastic Job | 2.1.5
Swagger Knife4j | 2.0.3


### 目录结构说明
```lua
liyz
├── common-parent -- 它是整个项目的父pom文件，里面主要包含了Springboot框架的版本，和一些常用工具包，方便以后升级版本
└── common-base -- 基本包，也是整个项目的基本包。里面主要增加了一个Dubbo框架的处理
	└── @Desensitization -- 自定义脱敏，这个自定义脱敏是基于fastjson，用法如下 注：我将springboot默认的jsckson序列化方式修改成了fastjson
	
	```java
	@Desensitization(endIndex = 3)
	private String loginName;

	private String nickName;

	@Desensitization(DesensitizationType.REAL_NAME)
	private String userName;

	@Desensitization(DesensitizationType.MOBILE)
	private String mobile;

	@Desensitization(DesensitizationType.EMAIL)
	private String email;
	```
	
├── common-dao -- 服务中dao层需要引用，定义了通用mapper的顶层接口（mapper）、service层的顶层接口以及抽象类，所有大家对于单表操作不再需要维护一个*Mapper.xml文件了，当然了我在这里也提倡大家尽量单表操作，将多表关系在业务层来实现
└── common-security -- Springboot的安全配置
	└── @Anonymous -- 加在方法或者类上，代表这个方法或者该类下所有的mapping方法可以免鉴权访问，否则所有的api必须登录后写到token来访问
└── ommon-controller -- 是api controller服务需要引用的
	└── @Limits、@Limit -- 限流注解，可以对IP、mapping、以及总调用次数进行限流
├── common-export -- 自定义注解导出，只需要在实体类的field加上注解（Export即可），并且暂时只有csv的导出，如果需要excel的导出，大家可以自行添加
├── common-task -- 分布式定时任务，基于当当的 elastic job，当中有两个自定义注解：ElasticDataFlowJob、ElasticSimpleJob
├── service-member -- 用户服务，可以自己扩展
├── service-push -- 是一个基于netty的一个实时推送服务，当中的登陆依赖了 service-member，如果有认证中心或者需要修改认证的地方，可以自行修改
├── service-task -- 分布式定时任务调度中心，具体的业务代码写在各个服务中，这个项目只用来触发，通过dubbo来远程调用
├── api-web -- 对外统一的api出口，当然了大家也可以在每个服务对外开放api，看情况而定
└── service-file -- 文件上传下载服务

```

#### 开源共建
1.如有问题可以提交[issue](https://github.com/ZhiQinIsZhen/springcloud-demo/issues)

2.如有需要Dubbo项目，请点击[Spring Boot + Dubbo](https://github.com/ZhiQinIsZhen/dubbo-springboot-project)