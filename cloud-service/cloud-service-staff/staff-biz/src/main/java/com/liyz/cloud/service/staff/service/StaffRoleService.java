package com.liyz.cloud.service.staff.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liyz.cloud.service.staff.model.StaffRoleDO;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/15 14:13
 */
public interface StaffRoleService extends IService<StaffRoleDO> {

    /**
     * 根据staffId获取角色信息
     *
     * @param staffId 员工id
     * @return
     */
    List<StaffRoleDO> listByStaffId(Long staffId);
}
