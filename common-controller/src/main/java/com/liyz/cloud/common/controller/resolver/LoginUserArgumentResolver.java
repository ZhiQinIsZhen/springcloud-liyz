package com.liyz.cloud.common.controller.resolver;

import com.liyz.cloud.common.base.remote.LoginInfoService;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.controller.annotation.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/12/16 22:13
 */
@Slf4j
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private LoginInfoService loginInfoService;

    public LoginUserArgumentResolver(LoginInfoService loginInfoService) {
        this.loginInfoService = loginInfoService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        JwtUserBO loginUser = loginInfoService.getUser();
        return loginUser;
    }
}
