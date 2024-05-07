package com.liyz.cloud.service.auth.bo;

import com.liyz.cloud.common.base.constant.Device;
import com.liyz.cloud.common.base.constant.LoginType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/14 14:02
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserLoginBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Long authId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

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

    @Schema(description = "IP地址")
    private String ip;

    @Schema(description = "客户端ID")
    private String clientId;
}
