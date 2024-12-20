package com.liyz.cloud.common.feign.bo.auth;

import com.liyz.cloud.common.feign.constant.Device;
import com.liyz.cloud.common.feign.constant.LoginType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/24 10:41
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6046110472442516409L;

    @Schema(description = "用户ID")
    private Long authId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "加密盐")
    private String salt;

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "token")
    private String token;

    /**
     * 登录类型
     * @see LoginType
     */
    @Schema(description = "登录类型")
    private LoginType loginType;

    /**
     * 登录设备
     * @see Device
     */
    @Schema(description = "登录设备")
    private Device device;

    @Schema(description = "登录验证key")
    private String loginKey;

    @Schema(description = "用户角色")
    private List<Integer> roleIds;

    @Schema(description = "权限列表")
    private List<AuthGrantedAuthorityBO> authorities = new ArrayList<>();

    @Getter
    @Setter
    public static class AuthGrantedAuthorityBO implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @Schema(description = "客户端ID")
        private String clientId;

        @Schema(description = "权限码")
        private String authorityCode;
    }
}
