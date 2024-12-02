package com.liyz.cloud.service.staff.dto.log;

import com.liyz.cloud.common.feign.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/10/30 15:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StaffLogPageDTO extends PageDTO {

    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long staffId;
}
