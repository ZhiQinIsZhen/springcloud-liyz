package com.liyz.cloud.common.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/28 13:29
 */
@Configuration
public class TomcatConfig {

    @Value("${spring.multipart.maxFileSize}")
    private String MaxFileSize;
    @Value("${spring.multipart.maxRequestSize}")
    private String MaxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(DataSize.ofMegabytes(Long.valueOf(MaxFileSize))); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(Long.valueOf(MaxRequestSize)));
        return factory.createMultipartConfig();
    }
}
