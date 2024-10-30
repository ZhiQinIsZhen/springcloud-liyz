package com.liyz.cloud.service.staff.bo.role;

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
 * @date 2023/3/11 13:23
 */
@Getter
@Setter
public class SystemRoleBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2197798732348814224L;

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "父角色ID")
    private Integer parentRoleId;
}
