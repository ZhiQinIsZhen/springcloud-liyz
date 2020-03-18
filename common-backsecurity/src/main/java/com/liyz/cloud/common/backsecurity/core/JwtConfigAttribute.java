package com.liyz.cloud.common.backsecurity.core;

import lombok.Getter;
import org.springframework.security.access.ConfigAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/18 16:10
 */
public class JwtConfigAttribute implements ConfigAttribute {

    @Getter
    private final HttpServletRequest httpServletRequest;

    public JwtConfigAttribute(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public String getAttribute() {
        return null;
    }
}
