package com.liyz.cloud.common.feign.dto.auth;

import com.liyz.cloud.common.feign.constant.Device;
import com.liyz.cloud.common.feign.constant.LoginType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/14 14:14
 */
@Getter
@Setter
public class AuthUserLogoutDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Long authId;

    /**
     * 登录类型
     * @see com.liyz.cloud.common.feign.constant.LoginType
     */
    @Schema(description = "登录类型")
    private LoginType logoutType;

    /**
     * 登录设备
     * @see com.liyz.cloud.common.feign.constant.Device
     */
    @Schema(description = "登录设备")
    private Device device;

    @Schema(description = "IP地址")
    private String ip;

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "登录验证key")
    private String loginKey;
}
