package com.liyz.cloud.api.staff.controller.auth;

import com.liyz.cloud.api.staff.bo.AuthUserBO;
import com.liyz.cloud.api.staff.bo.AuthUserLoginBO;
import com.liyz.cloud.api.staff.bo.AuthUserRegisterBO;
import com.liyz.cloud.api.staff.dto.auth.StaffLoginDTO;
import com.liyz.cloud.api.staff.dto.auth.StaffRegisterDTO;
import com.liyz.cloud.api.staff.vo.auth.AuthLoginVO;
import com.liyz.cloud.common.api.annotation.Anonymous;
import com.liyz.cloud.common.api.constant.SecurityClientConstant;
import com.liyz.cloud.common.base.result.Result;
import com.liyz.cloud.common.base.util.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/5/9 15:10
 */
@Tag(name = "员工信息")
@ApiResponses(value = {
        @ApiResponse(responseCode = "0", description = "成功"),
        @ApiResponse(responseCode = "1", description = "失败")
})
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Anonymous
    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result<Boolean> register(@Validated({StaffRegisterDTO.Register.class}) @RequestBody StaffRegisterDTO staffRegister) {
        return Result.success(AuthContext.AuthService.registry(BeanUtil.copyProperties(staffRegister, AuthUserRegisterBO::new)));
    }

    @Anonymous
    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<AuthLoginVO> login(@Validated({StaffLoginDTO.Login.class}) @RequestBody StaffLoginDTO loginDTO) throws IOException {
        AuthUserBO authUserBO = AuthContext.AuthService.login(BeanUtil.copyProperties(loginDTO, AuthUserLoginBO::new));
        AuthLoginVO authLoginVO = new AuthLoginVO();
        authLoginVO.setToken(authUserBO.getToken());
        authLoginVO.setExpiration(AuthContext.JwtService.getExpiration(authUserBO.getToken()));
        if (StringUtils.isNotBlank(loginDTO.getRedirect())) {
            HttpServletResponse response = HttpServletContext.getResponse();
            response.setHeader(SecurityClientConstant.DEFAULT_TOKEN_HEADER_KEY, authLoginVO.getToken());
            response.sendRedirect(loginDTO.getRedirect());
        }
        return Result.success(authLoginVO);
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public Result<Boolean> logout() {
        return Result.success(AuthContext.AuthService.logout());
    }
}
