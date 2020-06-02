package com.liyz.cloud.common.backsecurity.handler;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 注释:认证失败事件处理器
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/1 17:54
 */
public abstract class AbstractAuthenticationFailureEvenHandler implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        AuthenticationException authenticationException = event.getException();
        Authentication authentication = (Authentication) event.getSource();
        handle(authenticationException, authentication);
    }

    /**
     * 处理登录成功方法
     *
     * @param authenticationException
     * @param authentication
     */
    public abstract void handle(AuthenticationException authenticationException, Authentication authentication);
}
