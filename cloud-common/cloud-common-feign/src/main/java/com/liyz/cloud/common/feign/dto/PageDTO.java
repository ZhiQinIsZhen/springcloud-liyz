package com.liyz.cloud.common.feign.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 10:28
 */
@Getter
@Setter
public class PageDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "页码")
    @NotNull(groups = {PageQuery.class}, message = "分页查询页码不能为空")
    private Long pageNum = 1L;

    @Schema(description = "每页条数")
    @NotNull(groups = {PageQuery.class}, message = "分页查询每页数量不能为空")
    private Long pageSize = 10L;

    public static PageDTO of(long pageNum, long pageSize) {
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageSize(Math.max(pageNum, 1L));
        pageDTO.setPageSize(Math.max(pageSize, 1L));
        return pageDTO;
    }

    public interface PageQuery {}
}
