package com.liyz.cloud.service.staff.feign.auth;

import com.liyz.cloud.common.base.result.Result;
import com.liyz.cloud.service.staff.biz.FeignAuthBiz;
import com.liyz.cloud.service.staff.bo.auth.AuthUserBO;
import com.liyz.cloud.service.staff.bo.auth.AuthUserLoginBO;
import com.liyz.cloud.service.staff.bo.auth.AuthUserLogoutBO;
import com.liyz.cloud.service.staff.bo.auth.AuthUserRegisterBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/30 13:38
 */
@Tag(name = "客户鉴权")
@RestController
@RequestMapping("/auth")
public class FeignAuthController {

    @Resource
    private FeignAuthBiz feignAuthBiz;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<Boolean> registry(@Validated({AuthUserRegisterBO.Register.class}) @RequestBody AuthUserRegisterBO authUserRegister) {
        return Result.success(feignAuthBiz.registry(authUserRegister));
    }

    @Operation(summary = "根据用户名查询用户信息")
    @PostMapping("/loadByUsername")
    public Result<AuthUserBO> loadByUsername(@Validated({AuthUserBO.Load.class}) @RequestBody AuthUserBO authUserBO) {
        return Result.success(feignAuthBiz.loadByUsername(authUserBO.getUsername(), authUserBO.getDevice()));
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<Date> login(@RequestBody AuthUserLoginBO authUserLogin) {
        return Result.success(feignAuthBiz.login(authUserLogin));
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public Result<Boolean> logout(@RequestBody AuthUserLogoutBO authUserLogout) {
        return Result.success(feignAuthBiz.logout(authUserLogout));
    }

    @Operation(summary = "获取权限列表")
    @PostMapping("/authorities")
    public Result<List<AuthUserBO.AuthGrantedAuthorityBO>> authorities(@RequestBody AuthUserBO authUser) {
        return Result.success(feignAuthBiz.authorities(authUser));
    }
}
