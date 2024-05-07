package com.liyz.cloud.service.auth.feign.staff;

import com.liyz.cloud.common.base.result.Result;
import com.liyz.cloud.service.auth.bo.AuthUserBO;
import com.liyz.cloud.service.auth.bo.AuthUserLoginBO;
import com.liyz.cloud.service.auth.bo.AuthUserLogoutBO;
import com.liyz.cloud.service.auth.bo.AuthUserRegisterBO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/30 17:47
 */
@FeignClient(value = "cloud-service-staff", contextId = "StaffInfo", path = "/staff/auth")
public interface FeignStaffAuthService {

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    Result<Boolean> registry(@RequestBody AuthUserRegisterBO authUserRegister);

    @Operation(summary = "根据用户名查询用户信息")
    @PostMapping("/loadByUsername")
    Result<AuthUserBO> loadByUsername(@RequestBody AuthUserBO authUserBO);

    @Operation(summary = "登录")
    @PostMapping("/login")
    Result<Date> login(@RequestBody AuthUserLoginBO authUserLogin);

    @Operation(summary = "登出")
    @PostMapping("/logout")
    Result<Boolean> logout(@RequestBody AuthUserLogoutBO authUserLogout);

    @Operation(summary = "获取权限列表")
    @PostMapping("/authorities")
    Result<List<AuthUserBO.AuthGrantedAuthorityBO>> authorities(@RequestBody AuthUserBO authUser);
}
