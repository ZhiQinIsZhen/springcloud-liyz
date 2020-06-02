package com.liyz.cloud.common.backsecurity.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyz.cloud.common.base.Result.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 注释:授权拒绝处理器，覆盖默认的OAuth2AccessDeniedHandler
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/1 16:30
 */
@Slf4j
@Component
public class OAuth2BackAccessDeniedHandler extends OAuth2AccessDeniedHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) {
        log.info("OAuth2BackAccessDeniedHandler 授权失败，禁止访问 {}", request.getRequestURI());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Result result = Result.error(String.valueOf(HttpStatus.FORBIDDEN.hashCode()), HttpStatus.FORBIDDEN.getReasonPhrase());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
