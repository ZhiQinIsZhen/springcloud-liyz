package com.liyz.cloud.common.controller.config;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;
import com.liyz.cloud.common.base.filter.DesensitizationContextValueFilter;
import com.liyz.cloud.common.base.remote.LoginInfoService;
import com.liyz.cloud.common.controller.resolver.LoginUserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.spring.web.json.Json;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 注释: webmvc 配置类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/14 19:08
 */
@ConditionalOnExpression("${use.fastjson}")
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${swagger.doc}")
    private Boolean swagger;

    @Autowired
    LoginInfoService loginInfoService;

    /**
     * 允许加载本地静态资源
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (swagger) {
            registry.addResourceHandler("/**")
                    .addResourceLocations("classpath:/META-INF/resources/");
        }
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LoginUserArgumentResolver(loginInfoService));
    }

    /**
     * 不使用springBoot自带的jackson
     * 实用alibaba的fastjson作为默认序列化工具
     *
     * @param converters
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setSerializeFilters(new DesensitizationContextValueFilter());
        fastJsonConfig.getSerializeConfig().put(Json.class, SwaggerJsonSerializer.instance);
        SerializeConfig.getGlobalInstance().propertyNamingStrategy = PropertyNamingStrategy.CamelCase;
        fastJsonHttpMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastJsonHttpMessageConverter);

        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converters.add(stringHttpMessageConverter);
    }

    private List<MediaType> getSupportedMediaTypes() {
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fastMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        fastMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        fastMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        fastMediaTypes.add(MediaType.APPLICATION_PDF);
        fastMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        fastMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        fastMediaTypes.add(MediaType.APPLICATION_XML);
        fastMediaTypes.add(MediaType.IMAGE_GIF);
        fastMediaTypes.add(MediaType.IMAGE_JPEG);
        fastMediaTypes.add(MediaType.IMAGE_PNG);
        fastMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        fastMediaTypes.add(MediaType.TEXT_HTML);
        fastMediaTypes.add(MediaType.TEXT_MARKDOWN);
        fastMediaTypes.add(MediaType.TEXT_PLAIN);
        fastMediaTypes.add(MediaType.TEXT_XML);
        fastMediaTypes.add(new MediaType("application", "vnd.spring-boot.actuator.v3+json"));
        return fastMediaTypes;
    }
}