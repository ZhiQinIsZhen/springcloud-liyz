package com.liyz.cloud.common.backsecurity.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/18 15:26
 */
public interface BackSecurityConstant {

    String BACKSTAGE_ROLE_ADMIN = "admin";

    String ALL_METHOD = "ALL";

    List<String> SECURITY_IGNORE_RESOURCES = Arrays.asList("/instances",
            "/actuator/**",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**");
}
