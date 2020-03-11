package com.liyz.cloud.api.web.controller.auth;

import com.liyz.cloud.api.web.dto.auth.LoginDTO;
import com.liyz.cloud.api.web.dto.auth.UserRegisterDTO;
import com.liyz.cloud.api.web.feign.member.FeignUserInfoService;
import com.liyz.cloud.api.web.vo.auth.LoginVO;
import com.liyz.cloud.api.web.vo.business.UserInfoVO;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.common.base.remote.LoginInfoService;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.controller.annotation.Limit;
import com.liyz.cloud.common.controller.annotation.Limits;
import com.liyz.cloud.common.controller.constant.LimitType;
import com.liyz.cloud.common.controller.util.HttpRequestUtil;
import com.liyz.cloud.common.model.bo.member.LoginUserInfoBO;
import com.liyz.cloud.common.model.bo.member.UserInfoBO;
import com.liyz.cloud.common.model.bo.member.UserRegisterBO;
import com.liyz.cloud.common.model.constant.member.MemberEnum;
import com.liyz.cloud.common.security.annotation.Anonymous;
import com.liyz.cloud.common.security.util.JwtAuthenticationUtil;
import com.liyz.cloud.common.security.util.JwtTokenAnalysisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 注释:登录鉴权
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/27 15:28
 */
@Api(value = "用户鉴权", tags = "用户鉴权")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 401, message = "暂无授权"),
        @ApiResponse(code = 404, message = "找不到资源"),
        @ApiResponse(code = 500, message = "服务器内部错误")
        })
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenAnalysisUtil jwtTokenAnalysisUtil;
    @Autowired
    LoginInfoService loginInfoService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    FeignUserInfoService feignUserInfoService;

    @ApiOperation(value = "登陆", notes = "登陆")
    @Limits(value = {@Limit(count = 10, type = LimitType.IP), @Limit(count = 10)})
    @Anonymous
    @PostMapping("/login")
    public Result<LoginVO> login(@Validated({LoginDTO.Login.class}) @RequestBody LoginDTO loginDTO) {
        HttpServletRequest httpServletRequest = HttpRequestUtil.getRequest();
        LiteDeviceResolver resolver = new LiteDeviceResolver();
        Device device = resolver.resolveDevice(httpServletRequest);
        String ip = HttpRequestUtil.getIpAddress(httpServletRequest);
        log.info("user login，ip:{}", ip);
        if (!doAuth(loginDTO)) {
            return Result.error(CommonCodeEnum.LoginFail);
        }
        LoginVO loginVO = loginToken(device);
        return Result.success(loginVO);
    }

    @ApiOperation(value = "注册", notes = "注册")
    @Limits(value = {@Limit(count = 10, type = LimitType.IP), @Limit(count = 10)})
    @Anonymous
    @PostMapping("/register")
    public Result<UserInfoVO> register(@Validated({UserRegisterDTO.Register.class}) @RequestBody UserRegisterDTO userRegisterDTO) {
        HttpServletRequest request = HttpRequestUtil.getRequest();
        LiteDeviceResolver resolver = new LiteDeviceResolver();
        String ip = HttpRequestUtil.getIpAddress(request);
        Device device = resolver.resolveDevice(request);
        log.info("user register，ip:{}", ip);
        UserRegisterBO bo = CommonConverterUtil.beanCopy(userRegisterDTO, UserRegisterBO.class);
        bo.setLoginPwd(passwordEncoder.encode(userRegisterDTO.getLoginPwd()));
        bo.setIp(ip);
        MemberEnum.DeviceEnum deviceEnum ;
        if(device.isMobile()){
            deviceEnum = MemberEnum.DeviceEnum.MOBILE;
        }else{
            deviceEnum = MemberEnum.DeviceEnum.WEB;
        }
        bo.setDeviceEnum(deviceEnum);
        Result<UserInfoBO> result = feignUserInfoService.register(bo);
        if (CommonCodeEnum.success.getCode().equals(result.getCode())) {
            return Result.success(CommonConverterUtil.beanCopy(result.getData(), UserInfoVO.class));
        }
        return Result.error(result.getCode(), result.getMessage());
    }

    private boolean doAuth(LoginDTO loginDTO) {
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

    private LoginVO loginToken(Device device) {
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
        Result<Date> result = feignUserInfoService.kickDownLine(downLineBO);
        Date date = null;
        if (CommonCodeEnum.success.getCode().equals(result.getCode())) {
            date = result.getData();
        }
        final UserDetails userDetails = JwtAuthenticationUtil.create(userInfo);
        final String token = jwtTokenAnalysisUtil.generateToken(userDetails, device, date, userInfo.getUserId());
        Date expirationDateFromToken = jwtTokenAnalysisUtil.getExpirationDateFromToken(token);
        Long expirationDate = expirationDateFromToken.getTime();
        LoginVO loginVO = CommonConverterUtil.beanCopy(userInfo, LoginVO.class);
        loginVO.setExpirationDate(expirationDate);
        loginVO.setToken(token);
        return loginVO;
    }
}
