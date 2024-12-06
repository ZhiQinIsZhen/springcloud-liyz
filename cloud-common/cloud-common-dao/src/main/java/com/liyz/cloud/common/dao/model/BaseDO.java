package com.liyz.cloud.common.dao.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/23 16:55
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    private Integer version;
}
