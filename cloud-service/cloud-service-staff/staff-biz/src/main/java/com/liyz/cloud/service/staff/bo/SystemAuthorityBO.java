package com.liyz.cloud.service.staff.bo;

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
 * @date 2023/3/11 13:31
 */
@Getter
@Setter
public class SystemAuthorityBO implements Serializable {
    @Serial
    private static final long serialVersionUID = -6116744036977982899L;

    @Schema(description = "权限ID")
    private Integer authorityId;

    @Schema(description = "权限码")
    private String authority;

    @Schema(description = "权限名称")
    private String authorityName;

    @Schema(description = "父权限ID")
    private Integer parentAuthorityId;

    @Schema(description = "应用ID")
    private String clientId;
}
