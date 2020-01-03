package com.liyz.cloud.common.elasticsearch.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/20 9:48
 */
@Slf4j
@Getter
public class ElasticSearchConfig {

    @Value("${elasticsearch.ip.address}")
    private String address;

    public static RestHighLevelClient client;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        String[] hosts = address.split(",");
        List<Node> nodes = new ArrayList<>();
        for (String host:hosts){
            nodes.add(new Node(new HttpHost(host, 9200, "http")));
        }
        Node[] nodesArr = nodes.toArray(new Node[nodes.size()]);
        client = new RestHighLevelClient(RestClient.builder(nodesArr));
        log.info("ElasticSearch client init success ....");
        return client;
    }
}
