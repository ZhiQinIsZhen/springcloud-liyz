package com.liyz.cloud.common.backsecurity.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyz.cloud.common.base.Result.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/1 17:03
 */
@Slf4j
@Component
public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        log.info("ResourceAuthExceptionEntryPoint enter ............");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Result result = Result.error(String.valueOf(HttpStatus.UNAUTHORIZED.hashCode()), authException.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
