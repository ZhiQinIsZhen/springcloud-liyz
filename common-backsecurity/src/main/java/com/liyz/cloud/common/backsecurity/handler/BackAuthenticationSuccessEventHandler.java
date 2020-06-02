package com.liyz.cloud.common.backsecurity.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/1 18:07
 */
@Slf4j
@Component
public class BackAuthenticationSuccessEventHandler extends AbstractAuthenticationSuccessEventHandler {

    @Override
    public void handle(Authentication authentication) {
        log.info("用户：{} 登录成功", authentication.getPrincipal());
    }
}
