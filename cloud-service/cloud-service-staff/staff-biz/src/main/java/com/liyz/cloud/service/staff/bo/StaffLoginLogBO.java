package com.liyz.cloud.service.staff.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:15
 */
@Getter
@Setter
public class StaffLoginLogBO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8978119199629210583L;

    @Schema(description = "用户ID")
    private Long staffId;

    @Schema(description = "用户方式(1:手机;2:邮箱)")
    private Integer loginType;

    @Schema(description = "用户设备")
    private Integer device;

    @Schema(description = "用户登录时间")
    private Date loginTime;

    @Schema(description = "用户登录IP")
    private String ip;
}
