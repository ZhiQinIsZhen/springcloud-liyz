package com.liyz.cloud.service.staff.feign;

import com.liyz.cloud.common.feign.result.Result;
import com.liyz.cloud.common.feign.bo.auth.AuthUserBO;
import com.liyz.cloud.service.staff.constants.StaffConstants;
import com.liyz.cloud.common.feign.dto.auth.AuthUserDTO;
import com.liyz.cloud.common.feign.dto.auth.AuthUserLoginDTO;
import com.liyz.cloud.common.feign.dto.auth.AuthUserLogoutDTO;
import com.liyz.cloud.common.feign.dto.auth.AuthUserRegisterDTO;
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
 * @date 2024/10/30 17:02
 */
@FeignClient(value = StaffConstants.APPLICATION_NAME, contextId = "StaffAuthFeign",
        path = StaffConstants.CONTEXT_PATH + StaffConstants.STAFF_AUTH_URL)
public interface StaffAuthFeignService {

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    Result<Boolean> registry(@RequestBody AuthUserRegisterDTO authUserRegister);

    @Operation(summary = "根据用户名查询用户信息")
    @PostMapping("/loadByUsername")
    Result<AuthUserBO> loadByUsername(@RequestBody AuthUserDTO authUser);

    @Operation(summary = "登录")
    @PostMapping("/login")
    Result<Date> login(@RequestBody AuthUserLoginDTO authUserLogin);

    @Operation(summary = "登出")
    @PostMapping("/logout")
    Result<Boolean> logout(@RequestBody AuthUserLogoutDTO authUserLogout);

    @Operation(summary = "获取权限列表")
    @PostMapping("/authorities")
    Result<List<AuthUserBO.AuthGrantedAuthorityBO>> authorities(@RequestBody AuthUserDTO authUser);
}
