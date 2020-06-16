package com.liyz.cloud.common.elasticsearch.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 注释:es properties
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 11:18
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.elasticsearch")
public class ElasticSearchProperties {

    private String address;

    private int port = 9200;

    private String scheme = "http";

    private int connectTimeout = 30 * 1000;

    private int socketTimeout = 30 * 1000;

    private int connectionRequestTimeout = 2 * 1000;
}
