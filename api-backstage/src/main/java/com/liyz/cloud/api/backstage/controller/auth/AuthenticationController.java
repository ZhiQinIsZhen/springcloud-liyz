package com.liyz.cloud.api.backstage.controller.auth;


import com.liyz.cloud.api.backstage.dto.auth.LoginDTO;
import com.liyz.cloud.api.backstage.dto.auth.UserRegisterDTO;
import com.liyz.cloud.api.backstage.feign.member.FeignUserAdminInfoService;
import com.liyz.cloud.api.backstage.vo.auth.LoginVO;
import com.liyz.cloud.api.backstage.vo.auth.ResourcesVO;
import com.liyz.cloud.api.backstage.vo.member.UserAdminInfoVO;
import com.liyz.cloud.common.backsecurity.core.JwtGrantedAuthority;
import com.liyz.cloud.common.backsecurity.util.JwtAuthenticationUtil;
import com.liyz.cloud.common.backsecurity.util.JwtTokenAnalysisUtil;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.common.base.exception.RemoteServiceException;
import com.liyz.cloud.common.base.remote.LoginInfoService;
import com.liyz.cloud.common.base.remote.RemoteResourcesService;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.base.remote.bo.ResourcesBO;
import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.controller.annotation.Limit;
import com.liyz.cloud.common.controller.annotation.Limits;
import com.liyz.cloud.common.controller.constant.LimitType;
import com.liyz.cloud.common.controller.util.HttpRequestUtil;
import com.liyz.cloud.common.model.bo.member.LoginUserInfoBO;
import com.liyz.cloud.common.model.bo.member.UserAdminInfoBO;
import com.liyz.cloud.common.model.bo.member.UserRegisterBO;
import com.liyz.cloud.common.model.constant.member.MemberEnum;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    FeignUserAdminInfoService feignUserAdminInfoService;
    @Autowired
    RemoteResourcesService remoteResourcesService;

    @ApiOperation(value = "登陆", notes = "登陆")
    @Limits(value = {@Limit(count = 10, type = LimitType.IP), @Limit(count = 10)})
    @PostMapping("/login")
    public Result<LoginVO> login(@Validated({LoginDTO.Login.class}) @RequestBody LoginDTO loginDTO) {
        HttpServletRequest httpServletRequest = HttpRequestUtil.getRequest();
        LiteDeviceResolver resolver = new LiteDeviceResolver();
        Device device = resolver.resolveDevice(httpServletRequest);
        String ip = HttpRequestUtil.getIpAddress(httpServletRequest);
        log.info("user login，ip:{}", ip);
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginDTO.getLoginName(), loginDTO.getLoginPwd());
        final Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginVO loginVO = loginToken(device, ip);
        return Result.success(loginVO);
    }

    @ApiOperation(value = "注册", notes = "注册")
    @Limits(value = {@Limit(count = 10, type = LimitType.IP), @Limit(count = 10)})
    @PostMapping("/register")
    public Result<UserAdminInfoVO> register(@Validated({UserRegisterDTO.Register.class}) @RequestBody UserRegisterDTO userRegisterDTO) {
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
        Result<UserAdminInfoBO> result = feignUserAdminInfoService.register(bo);
        if (CommonCodeEnum.success.getCode().equals(result.getCode())) {
            return Result.success(CommonConverterUtil.beanCopy(result.getData(), UserAdminInfoVO.class));
        }
        return Result.error(result.getCode(), result.getMessage());
    }

    private LoginVO loginToken(Device device, String ip) {
        MemberEnum.DeviceEnum deviceEnum ;
        deviceEnum = MemberEnum.DeviceEnum.WEB;
        JwtUserBO userInfo = loginInfoService.getUser();
        LoginUserInfoBO downLineBO = new LoginUserInfoBO();
        downLineBO.setUserId(userInfo.getUserId());
        downLineBO.setDeviceEnum(deviceEnum);
        downLineBO.setIp(ip);
        Result<Date> result = feignUserAdminInfoService.loginTime(downLineBO);
        Date date;
        if (CommonCodeEnum.success.getCode().equals(result.getCode())) {
            date = result.getData();
        } else {
            throw new RemoteServiceException(CommonCodeEnum.LoginError);
        }
        List<ResourcesBO> boList = remoteResourcesService.list(userInfo.getUserId());
        int size = CollectionUtils.isEmpty(boList) ? 10 : boList.size();
        List<GrantedAuthority> authorities = new ArrayList<>(size);
        boList.forEach(resourcesBO -> {
            GrantedAuthority grantedAuthority = new JwtGrantedAuthority(resourcesBO.getUrl(),
                    resourcesBO.getMethod());
            authorities.add(grantedAuthority);
        });
        final UserDetails userDetails = JwtAuthenticationUtil.create(userInfo, authorities);
        final String token = jwtTokenAnalysisUtil.generateToken(userDetails, device, date, userInfo.getUserId());
        Date expirationDateFromToken = jwtTokenAnalysisUtil.getExpirationDateFromToken(token);
        Long expirationDate = expirationDateFromToken.getTime();
        LoginVO loginVO = CommonConverterUtil.beanCopy(userInfo, LoginVO.class);
        loginVO.setExpirationDate(expirationDate);
        loginVO.setToken(token);
        loginVO.setResourcesList(CommonConverterUtil.ListTransform(boList, ResourcesVO.class));
        return loginVO;
    }
}
