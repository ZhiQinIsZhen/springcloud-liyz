package com.liyz.cloud.common.api.bo;

import com.liyz.cloud.common.base.constant.Device;
import com.liyz.cloud.common.base.constant.LoginType;
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
public class AuthUserLogoutBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long authId;

    /**
     * 登录类型
     * @see LoginType
     */
    private LoginType logoutType;

    /**
     * 登录设备
     * @see Device
     */
    private Device device;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 客户端ID
     */
    private String clientId;
}
