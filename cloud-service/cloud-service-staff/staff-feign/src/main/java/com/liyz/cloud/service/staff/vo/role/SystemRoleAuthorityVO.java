package com.liyz.cloud.service.staff.vo.role;

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
 * @date 2023/3/11 15:33
 */
@Getter
@Setter
public class SystemRoleAuthorityVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8328113920009535585L;

    @Schema(description = "角色ID")
    private Integer roleId;

    @Schema(description = "权限ID")
    private Integer authorityId;
}
