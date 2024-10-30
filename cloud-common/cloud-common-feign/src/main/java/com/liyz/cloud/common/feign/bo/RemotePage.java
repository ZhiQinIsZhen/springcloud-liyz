package com.liyz.cloud.common.feign.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 10:30
 */
public class RemotePage<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public RemotePage() {
    }

    public RemotePage(List<T> list, long total, long pageNum, long pageSize) {
        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Getter
    @Setter
    @Schema(description = "结果集")
    private List<T> list;

    @Getter
    @Setter
    @Schema(description = "总记录数")
    private long total;

    @Getter
    @Schema(description = "当前页")
    private long pageNum;

    @Getter
    @Schema(description = "每页的数量")
    private long pageSize;


    public void setPageNum(long pageNum) {
        this.pageNum = Math.max(1L, pageNum);
    }

    public void setPageSize(long pageSize) {
        this.pageSize = Math.max(1L, pageSize);
    }

    public long getPages() {
        return this.total % this.pageSize == 0 ? this.total / this.pageSize : this.total / this.pageSize + 1;
    }

    public boolean isHasNextPage() {
        return getPages() > pageNum;
    }

    public static <T> RemotePage<T> of() {
        return new RemotePage<>(List.of(), 0, 1, 10);
    }

    public static <T> RemotePage<T> of(List<T> list, long total, long pageNum, long pageSize) {
        return new RemotePage<>(list, total, pageNum, pageSize);
    }
}