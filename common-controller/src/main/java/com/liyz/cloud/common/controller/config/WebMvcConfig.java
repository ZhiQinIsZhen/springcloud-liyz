package com.liyz.cloud.common.controller.config;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.liyz.cloud.common.base.filter.FastjsonDesensitizationContextValueFilter;
import com.liyz.cloud.common.base.remote.LoginInfoService;
import com.liyz.cloud.common.controller.resolver.LoginUserArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.spring.web.json.Json;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * 注释: webmvc 配置类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/14 19:08
 */
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${swagger.doc}")
    private Boolean swagger;
    @Value("${use.fastjson:false}")
    private Boolean useFastJson;

    @Autowired
    LoginInfoService loginInfoService;

    /**
     * 允许加载本地静态资源
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (swagger && useFastJson) {
            registry.addResourceHandler("/**")
                    .addResourceLocations("classpath:/META-INF/resources/");
        }
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LoginUserArgumentResolver(loginInfoService));
        super.addArgumentResolvers(argumentResolvers);
    }

    /**
     * 不使用springBoot自带的jackson
     * 实用alibaba的fastjson作为默认序列化工具
     *
     * @param converters
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (useFastJson) {
            log.info("upload fastjson ............");
            FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
            FastJsonConfig fastJsonConfig = new FastJsonConfig();
            fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
            fastJsonConfig.setSerializeFilters(new FastjsonDesensitizationContextValueFilter());
            fastJsonConfig.getSerializeConfig().put(Json.class, SwaggerJsonSerializer.instance);
            SerializeConfig.getGlobalInstance().propertyNamingStrategy = PropertyNamingStrategy.CamelCase;
            fastJsonHttpMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());
            fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
            converters.add(fastJsonHttpMessageConverter);

            StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
            converters.add(stringHttpMessageConverter);
        } else {
            super.configureMessageConverters(converters);
        }
    }

    private List<MediaType> getSupportedMediaTypes() {
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.ALL);
        return fastMediaTypes;
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = converter.getObjectMapper();
        // 生成JSON时,将所有Long转换成String
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        // 时间格式化
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        objectMapper.enable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 设置格式化内容
        converter.setObjectMapper(objectMapper);
        converters.add(0, converter);
    }
}