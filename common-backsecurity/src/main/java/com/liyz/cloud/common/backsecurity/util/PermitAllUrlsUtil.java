package com.liyz.cloud.common.backsecurity.util;

import com.liyz.cloud.common.backsecurity.annotation.Anonymous;
import com.liyz.cloud.common.backsecurity.constant.BackSecurityConstant;
import com.liyz.cloud.common.base.util.SpringContextUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/5/29 17:42
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PermitAllUrlsUtil {

    private static volatile List<String> anonymousUrls = null;

    /**
     * 获取免鉴权的urls {@link Anonymous}
     *
     * @return
     */
    public static List<String> anonymousUrls() {
        if (anonymousUrls == null) {
            synchronized (PermitAllUrlsUtil.class) {
                if (anonymousUrls == null) {
                    List<String> list = new ArrayList<>();
                    Map<String, Object> map = SpringContextUtil.getBeansWithAnnotation(Controller.class);
                    if (CollectionUtils.isEmpty(map)) {
                        return list;
                    }
                    Class beanClass;
                    for (Object bean : map.values()) {
                        //获取原始类而不是代理类
                        beanClass = AopUtils.isAopProxy(bean) ? AopUtils.getTargetClass(bean) : bean.getClass();
                        scanMethods(beanClass, list);
                    }
                    anonymousUrls = list;
                }
            }
        }
        return anonymousUrls;
    }

    /**
     * 获取类上的注解 {@link RequestMapping} 的value
     *
     * @param beanClass
     * @return
     */
    private static String classMapping(Class beanClass) {
        String classMapping = "";
        if (beanClass.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = (RequestMapping) beanClass.getAnnotation(RequestMapping.class);
            String[] mappings = requestMapping.value();
            if (mappings != null && mappings.length > 0) {
                classMapping = mappings[0];
            }
        }
        return classMapping;
    }

    /**
     * 获取方法上的mapping
     *
     * @param method
     * @return
     */
    private static String methodMapping(Method method) {
        String[] methodMappings = null;
        if (method.isAnnotationPresent(RequestMapping.class)) {
            methodMappings = method.getAnnotation(RequestMapping.class).value();
        } else if (method.isAnnotationPresent(GetMapping.class)) {
            methodMappings = method.getAnnotation(GetMapping.class).value();
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            methodMappings = method.getAnnotation(PutMapping.class).value();
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            methodMappings = method.getAnnotation(PostMapping.class).value();
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            methodMappings = method.getAnnotation(DeleteMapping.class).value();
        }
        if (methodMappings != null && methodMappings.length > 0) {
            String mapping =  methodMappings[0];
            if (mapping.contains("{")) {
                mapping = mapping.substring(0, mapping.indexOf("{")) + BackSecurityConstant.ASTERISK;
            }
            return mapping;
        }
        return null;
    }

    /**
     * 扫描类上的方法获取url
     *
     * @param beanClass
     * @param list
     */
    private static void scanMethods(Class beanClass, List<String> list) {
        String classMapping = classMapping(beanClass);
        boolean classHaveAnonymous = beanClass.isAnnotationPresent(Anonymous.class);
        Method[] methods = beanClass.getDeclaredMethods();
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                classHaveAnonymous = classHaveAnonymous ? classHaveAnonymous : method.isAnnotationPresent(Anonymous.class);
                if (classHaveAnonymous) {
                    String methodMapping = methodMapping(method);
                    if (StringUtils.isNotBlank(methodMapping)) {
                        list.add(classMapping + methodMapping);
                    }
                }
            }
        }
    }
}
