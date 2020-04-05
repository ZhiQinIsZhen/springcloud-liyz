package com.liyz.cloud.common.elasticsearch.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 11:17
 */
@EnableConfigurationProperties(ElasticSearchProperties.class)
public class ElasticSearchConfig {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    ElasticSearchProperties properties;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        String[] hosts = properties.getAddress().split(",");
        if (hosts != null && hosts.length > 0) {
            HttpHost[] httpHosts = new HttpHost[hosts.length];
            int count = 0;
            for (String host : hosts){
                httpHosts[count] = new HttpHost(host, properties.getPort(), properties.getScheme());
                count++;
            }
            RestClientBuilder restClientBuilder = RestClient.builder(httpHosts).setRequestConfigCallback(
                    new RestClientBuilder.RequestConfigCallback() {
                        @Override
                        public RequestConfig.Builder customizeRequestConfig(
                                RequestConfig.Builder requestConfigBuilder) {
                            requestConfigBuilder.setConnectTimeout(properties.getConnectTimeout());
                            requestConfigBuilder.setSocketTimeout(properties.getSocketTimeout());
                            requestConfigBuilder.setConnectionRequestTimeout(properties.getConnectionRequestTimeout());
                            return requestConfigBuilder;
                        }
                    }
            );
            restClientBuilder.setFailureListener(new RestClient.FailureListener() {
                @Override
                public void onFailure(Node node) {
                    logger.error("************************es 监听器 failure:" + node.getName());
                }
            });
            restClientBuilder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
            RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);
            logger.info("ElasticSearch client init success ....");
            return client;
        }
        return null;
    }
}
