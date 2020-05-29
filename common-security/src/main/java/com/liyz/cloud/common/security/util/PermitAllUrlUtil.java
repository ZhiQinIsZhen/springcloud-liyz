package com.liyz.cloud.common.security.util;

import com.liyz.cloud.common.base.util.SpringContextUtil;
import com.liyz.cloud.common.security.annotation.Anonymous;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/5/29 17:42
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PermitAllUrlUtil {

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    public static List<String> anonymousUrls() {
        List<String> anonymousUrls = new ArrayList<>();
        RequestMappingHandlerMapping mapping = SpringContextUtil.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        Anonymous anonymous;
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            anonymous = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Anonymous.class);
            if (anonymous == null) {
                anonymous = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Anonymous.class);
                if (anonymous == null) {
                    continue;
                }

            }
        }
        return anonymousUrls;
    }
}
