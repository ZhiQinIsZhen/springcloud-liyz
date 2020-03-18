package com.liyz.cloud.common.backsecurity.core;

import com.liyz.cloud.common.backsecurity.constant.BackSecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/18 15:24
 */
@Slf4j
@Service
public class JwtAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        //如果身份是管理员或者是一些不需要验证的url，直接通过
        if (BackSecurityConstant.BACKSTAGE_ROLE_ADMIN.equals(authentication.getPrincipal()) || matchUrl(null, request)) {
            return;
        }
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            if (ga instanceof JwtGrantedAuthority) {
                JwtGrantedAuthority jwtGrantedAuthority = (JwtGrantedAuthority) ga;
                String url = jwtGrantedAuthority.getUrl();
                String method = jwtGrantedAuthority.getMethod();
                if (matchUrl(url, request)) {
                    if (BackSecurityConstant.ALL_METHOD.equals(method) || method.equals(request.getMethod())) {
                        return;
                    }
                }
            }
        }
        throw new AccessDeniedException("no right");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    private boolean matchUrl(String url, HttpServletRequest request) {
        if (!StringUtils.isBlank(url)) {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
            if (matcher.matches(request)) {
                return true;
            } else {
                return false;
            }
        }
        for (String resource : BackSecurityConstant.SECURITY_IGNORE_RESOURCES) {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(resource);
            if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    }
}
