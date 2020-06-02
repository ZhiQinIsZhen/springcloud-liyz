package com.liyz.cloud.common.backsecurity.component;

import com.liyz.cloud.common.backsecurity.util.PermitAllUrlsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * 注释:改造 {@link BearerTokenExtractor} 对公开权限的请求不进行校验
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/1 16:43
 */
@Slf4j
@Component
public class BackBearerTokenExtractor extends BearerTokenExtractor {

    private final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Authentication extract(HttpServletRequest request) {
        boolean match = PermitAllUrlsUtil.anonymousUrls().stream()
                .anyMatch(url -> pathMatcher.match(url, request.getRequestURI()));
        log.info("BackBearerTokenExtractor uri:{}, match:{}", request.getRequestURI(), match);
        return match ? null : super.extract(request);
    }
}
