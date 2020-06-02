package com.liyz.cloud.common.backsecurity.handler;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;

/**
 * 注释:认证成功事件处理器
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/1 17:56
 */
public abstract class AbstractAuthenticationSuccessEventHandler implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = (Authentication) event.getSource();
        if (!CollectionUtils.isEmpty(authentication.getAuthorities())) {
            handle(authentication);
        }
    }

    /**
     * 处理登录成功方法
     *
     * @param authentication
     */
    public abstract void handle(Authentication authentication);
}
