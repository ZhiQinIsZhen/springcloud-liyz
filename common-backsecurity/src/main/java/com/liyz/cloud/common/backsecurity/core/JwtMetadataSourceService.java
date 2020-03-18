package com.liyz.cloud.common.backsecurity.core;

import com.liyz.cloud.common.backsecurity.constant.BackSecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/18 16:13
 */
@Slf4j
@Service
public class JwtMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        if (!matchUrl(request)) {
            Set<ConfigAttribute> allAttributes = new HashSet<>();
            ConfigAttribute configAttribute = new JwtConfigAttribute(request);
            allAttributes.add(configAttribute);
            return allAttributes;
        } else {
            return null;
        }
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    private boolean matchUrl(HttpServletRequest request) {
        for (String resource : BackSecurityConstant.SECURITY_IGNORE_RESOURCES) {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(resource);
            if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    }
}
