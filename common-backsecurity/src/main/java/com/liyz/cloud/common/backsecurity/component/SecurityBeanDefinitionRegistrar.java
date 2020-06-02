package com.liyz.cloud.common.backsecurity.component;

import com.liyz.cloud.common.backsecurity.constant.BackSecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/1 17:09
 */
@Slf4j
public class SecurityBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * 根据注解值动态注入资源服务器的相关属性
     *
     * @param metadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        if (registry.isBeanNameInUse(BackSecurityConstant.RESOURCE_SERVER_CONFIGURER)) {
            log.warn("SecurityBeanDefinitionRegistrar 本地存在资源服务器配置，覆盖默认配置:" + BackSecurityConstant.RESOURCE_SERVER_CONFIGURER);
            return;
        }

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(ResourceServerConfigurerAdapter.class);
        registry.registerBeanDefinition(BackSecurityConstant.RESOURCE_SERVER_CONFIGURER, beanDefinition);

    }
}
