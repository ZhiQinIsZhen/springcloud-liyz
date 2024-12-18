package com.liyz.cloud.service.staff.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyz.cloud.service.staff.dao.StaffRoleMapper;
import com.liyz.cloud.service.staff.model.StaffRoleDO;
import com.liyz.cloud.service.staff.service.StaffRoleService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/15 14:13
 */
@Service
public class StaffRoleServiceImpl extends ServiceImpl<StaffRoleMapper, StaffRoleDO> implements StaffRoleService {

    /**
     * 根据staffId获取角色信息
     *
     * @param staffId 员工id
     * @return
     */
    @Override
    @Cacheable(cacheNames = {"auth"}, key = "'listByStaffId:' + #p0", unless = "#result == null")
    public List<StaffRoleDO> listByStaffId(Long staffId) {
        return list(Wrappers.query(StaffRoleDO.builder().staffId(staffId).build()));
    }
}
