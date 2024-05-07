package com.liyz.cloud.service.auth.feign;

import com.google.common.base.Splitter;
import com.liyz.cloud.common.base.constant.AuthExceptionCodeEnum;
import com.liyz.cloud.common.base.result.Result;
import com.liyz.cloud.common.base.util.BeanUtil;
import com.liyz.cloud.common.exception.CommonExceptionCodeEnum;
import com.liyz.cloud.common.exception.RemoteServiceException;
import com.liyz.cloud.common.util.RandomUtil;
import com.liyz.cloud.common.util.constant.CommonConstant;
import com.liyz.cloud.service.auth.bo.AuthUserBO;
import com.liyz.cloud.service.auth.bo.AuthUserLoginBO;
import com.liyz.cloud.service.auth.bo.AuthUserLogoutBO;
import com.liyz.cloud.service.auth.bo.AuthUserRegisterBO;
import com.liyz.cloud.service.auth.feign.staff.FeignStaffAuthService;
import com.liyz.cloud.service.auth.model.AuthSourceDO;
import com.liyz.cloud.service.auth.service.AuthSourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/30 17:47
 */
@Slf4j
@Tag(name = "客户鉴权")
@RestController
@RequestMapping("/auth")
public class FeignAuthController {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private AuthSourceService authSourceService;
    @Resource
    private FeignStaffAuthService feignStaffAuthService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<Boolean> registry(@RequestBody AuthUserRegisterBO authUserRegister) {
        if (StringUtils.isBlank(authUserRegister.getClientId())) {
            throw new RemoteServiceException(AuthExceptionCodeEnum.LACK_SOURCE_ID);
        }
        AuthSourceDO authSourceDO = authSourceService.getByClientId(authUserRegister.getClientId());
        if (Objects.isNull(authSourceDO)) {
            throw new RemoteServiceException(AuthExceptionCodeEnum.NON_SET_SOURCE_ID);
        }
        authUserRegister.setPassword(passwordEncoder.encode(authUserRegister.getPassword()));
        authUserRegister.setSalt(RandomUtil.randomChars(16));
        return feignStaffAuthService.registry(authUserRegister);
    }

    @Operation(summary = "根据用户名查询用户信息")
    @PostMapping("/loadByUsername")
    public Result<AuthUserBO> loadByUsername(@RequestBody AuthUserBO authUserBO) {
        List<String> names = Splitter.on(CommonConstant.DEFAULT_JOINER).splitToList(authUserBO.getUsername());
        AuthSourceDO authSourceDO = authSourceService.getByClientId(names.get(0));
        if (Objects.isNull(authSourceDO)) {
            log.warn("查询资源客户端ID失败，原因没有找到对应的配置信息，clientId : {}", names.get(0));
            throw new RemoteServiceException(AuthExceptionCodeEnum.LOGIN_ERROR);
        }
        Result<AuthUserBO> result = feignStaffAuthService.loadByUsername(BeanUtil.copyProperties(
                authUserBO,
                AuthUserBO::new,
                (s, t) -> t.setUsername(names.get(1)))
        );
        if (!CommonExceptionCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
            return Result.error(result.getCode(), result.getMessage());
        }
        AuthUserBO resultData = result.getData();
        if (Objects.nonNull(resultData)) {
            resultData.setDevice(authUserBO.getDevice());
            resultData.setClientId(names.get(0));
        }
        return Result.success(resultData);
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<Date> login(@RequestBody AuthUserLoginBO authUserLogin) {
        if (StringUtils.isBlank(authUserLogin.getClientId())) {
            log.warn("用户登录错误，原因 : clientId is blank");
            throw new RemoteServiceException(AuthExceptionCodeEnum.LOGIN_ERROR);
        }
        AuthSourceDO authSourceDO = authSourceService.getByClientId(authUserLogin.getClientId());
        if (Objects.isNull(authSourceDO)) {
            log.warn("查询资源客户端ID失败，原因没有找到对应的配置信息，clientId : {}", authUserLogin.getClientId());
            throw new RemoteServiceException(AuthExceptionCodeEnum.LOGIN_ERROR);
        }
        return feignStaffAuthService.login(authUserLogin);
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public Result<Boolean> logout(@RequestBody AuthUserLogoutBO authUserLogout) {
        if (StringUtils.isBlank(authUserLogout.getClientId())) {
            return Result.success(Boolean.FALSE);
        }
        AuthSourceDO authSourceDO = authSourceService.getByClientId(authUserLogout.getClientId());
        if (Objects.isNull(authSourceDO)) {
            return Result.success(Boolean.FALSE);
        }
        return feignStaffAuthService.logout(authUserLogout);
    }

    @Operation(summary = "获取权限列表")
    @PostMapping("/authorities")
    public Result<List<AuthUserBO.AuthGrantedAuthorityBO>> authorities(@RequestBody AuthUserBO authUser) {
        if (StringUtils.isBlank(authUser.getClientId())) {
            log.warn("获取权限错误，原因 : client is blank");
            return Result.success(new ArrayList<>());
        }
        AuthSourceDO authSourceDO = authSourceService.getByClientId(authUser.getClientId());
        if (Objects.isNull(authSourceDO)) {
            return Result.success(new ArrayList<>());
        }
        return feignStaffAuthService.authorities(authUser);
    }
}
