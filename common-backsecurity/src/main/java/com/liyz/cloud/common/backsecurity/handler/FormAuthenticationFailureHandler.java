package com.liyz.cloud.common.backsecurity.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注释:表单登录失败处理逻辑
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/1 17:58
 */
@Slf4j
public class FormAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.debug("表单登录失败:{}", exception.getLocalizedMessage());
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().sendRedirect(String.format("/token/login?error=%s", exception.getMessage()));
    }
}
