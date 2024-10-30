package com.liyz.cloud.service.staff.bo.info;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class StaffInfoBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 477985923412638468L;

    @Schema(description = "用户ID")
    private Long staffId;

    @Schema(description = "用户真实名称")
    private String realName;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "用户手机号码")
    private String mobile;

    @Schema(description = "用户邮箱地址")
    private String email;

    @Schema(description = "密码盐")
    private String salt;

    @Schema(description = "用户注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registryTime;
}
