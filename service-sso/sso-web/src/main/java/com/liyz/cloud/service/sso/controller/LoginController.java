package com.liyz.cloud.service.sso.controller;

import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.common.base.exception.RemoteServiceException;
import com.liyz.cloud.common.base.remote.LoginInfoService;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.base.util.DateUtil;
import com.liyz.cloud.common.controller.util.HttpRequestUtil;
import com.liyz.cloud.common.model.bo.member.LoginUserInfoBO;
import com.liyz.cloud.common.model.bo.member.UserInfoBO;
import com.liyz.cloud.common.model.constant.member.MemberEnum;
import com.liyz.cloud.service.sso.service.UserInfoService;
import com.liyz.cloud.service.sso.util.JwtAuthenticationUtil;
import com.liyz.cloud.service.sso.util.JwtTokenAnalysisUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/3 23:54
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenAnalysisUtil jwtTokenAnalysisUtil;
    @Autowired
    LoginInfoService loginInfoService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @ApiOperation(value = "登陆", notes = "登陆")
    @PostMapping("/login")
    public Result<UserInfoBO> login(@RequestBody UserInfoBO loginDTO) {
        HttpServletRequest httpServletRequest = HttpRequestUtil.getRequest();
        LiteDeviceResolver resolver = new LiteDeviceResolver();
        Device device = resolver.resolveDevice(httpServletRequest);
        String ip = HttpRequestUtil.getIpAddress(httpServletRequest);
        log.info("user login，ip:{}", ip);
        if (!doAuth(loginDTO)) {
            return Result.error(CommonCodeEnum.LoginFail);
        }
        UserInfoBO loginVO = loginToken(device, ip);
        return Result.success(loginVO);
    }

    private boolean doAuth(UserInfoBO loginDTO) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(loginDTO.getLoginName(), loginDTO.getLoginPwd());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (AuthenticationException e) {
            log.error("认证出错 method : {}", "doAuth");
            return false;
        }
    }

    private UserInfoBO loginToken(Device device, String ip) {
        MemberEnum.DeviceEnum deviceEnum ;
        if(device.isMobile()){
            deviceEnum = MemberEnum.DeviceEnum.MOBILE;
        }else{
            deviceEnum = MemberEnum.DeviceEnum.WEB;
        }
        JwtUserBO userInfo = loginInfoService.getUser();
        LoginUserInfoBO downLineBO = new LoginUserInfoBO();
        downLineBO.setUserId(userInfo.getUserId());
        downLineBO.setDeviceEnum(deviceEnum);
        downLineBO.setIp(ip);
        Result<Date> result = Result.success(DateUtil.currentDate());
        Date date;
        if (CommonCodeEnum.success.getCode().equals(result.getCode())) {
            date = result.getData();
        } else {
            throw new RemoteServiceException(CommonCodeEnum.LoginError);
        }
        final UserDetails userDetails = JwtAuthenticationUtil.create(userInfo);
        final String token = jwtTokenAnalysisUtil.generateToken(userDetails, device, date, userInfo.getUserId());
        Date expirationDateFromToken = jwtTokenAnalysisUtil.getExpirationDateFromToken(token);
        Long expirationDate = expirationDateFromToken.getTime();

        log.info("token:{}", token);
        log.info("expirationDate:{}", expirationDate);
        return CommonConverterUtil.beanCopy(userInfo, UserInfoBO.class);
    }
}
