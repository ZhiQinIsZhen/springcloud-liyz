package com.liyz.cloud.service.staff.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liyz.cloud.common.dao.model.BaseDO;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:00
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("staff_authority")
public class StaffAuthorityDO extends BaseDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3056945666918696574L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 员工ID
     */
    private Long staffId;

    /**
     * 权限ID
     */
    private Integer authorityId;

    /**
     * 权限过期时间
     */
    private Date authorityEndTime;
}
