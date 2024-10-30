package com.liyz.cloud.service.staff.controller.auth;

import com.liyz.cloud.common.feign.result.Result;
import com.liyz.cloud.service.staff.biz.FeignAuthBiz;
import com.liyz.cloud.common.feign.bo.auth.AuthUserBO;
import com.liyz.cloud.service.staff.constants.StaffConstants;
import com.liyz.cloud.common.feign.dto.auth.AuthUserDTO;
import com.liyz.cloud.common.feign.dto.auth.AuthUserLoginDTO;
import com.liyz.cloud.common.feign.dto.auth.AuthUserLogoutDTO;
import com.liyz.cloud.common.feign.dto.auth.AuthUserRegisterDTO;
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
@RequestMapping(StaffConstants.STAFF_AUTH_URL)
public class StaffAuthController {

    @Resource
    private FeignAuthBiz feignAuthBiz;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<Boolean> registry(@Validated({AuthUserRegisterDTO.Register.class}) @RequestBody AuthUserRegisterDTO authUserRegister) {
        return Result.success(feignAuthBiz.registry(authUserRegister));
    }

    @Operation(summary = "根据用户名查询用户信息")
    @PostMapping("/loadByUsername")
    public Result<AuthUserBO> loadByUsername(@Validated({AuthUserDTO.Load.class}) @RequestBody AuthUserDTO authUserDTO) {
        return Result.success(feignAuthBiz.loadByUsername(authUserDTO.getUsername(), authUserDTO.getDevice()));
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<Date> login(@RequestBody AuthUserLoginDTO authUserLogin) {
        return Result.success(feignAuthBiz.login(authUserLogin));
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public Result<Boolean> logout(@RequestBody AuthUserLogoutDTO authUserLogout) {
        return Result.success(feignAuthBiz.logout(authUserLogout));
    }

    @Operation(summary = "获取权限列表")
    @PostMapping("/authorities")
    public Result<List<AuthUserBO.AuthGrantedAuthorityBO>> authorities(@RequestBody AuthUserDTO authUser) {
        return Result.success(feignAuthBiz.authorities(authUser));
    }
}
