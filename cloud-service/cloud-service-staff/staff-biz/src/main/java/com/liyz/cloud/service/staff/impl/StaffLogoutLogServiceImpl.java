package com.liyz.cloud.service.staff.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyz.cloud.common.feign.constant.Device;
import com.liyz.cloud.service.staff.dao.StaffLogoutLogMapper;
import com.liyz.cloud.service.staff.model.StaffLogoutLogDO;
import com.liyz.cloud.service.staff.service.StaffLogoutLogService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/15 14:01
 */
@Service
public class StaffLogoutLogServiceImpl extends ServiceImpl<StaffLogoutLogMapper, StaffLogoutLogDO> implements StaffLogoutLogService {

    /**
     * 获取上次登出时间
     *
     * @param staffId 员工ID
     * @return 上次登出时间
     */
    @Override
    @Cacheable(cacheNames = {"auth"}, key = "'lastLogoutTime:' + #p0 + ':' + #p1.name()", unless = "#result == null")
    public Date lastLogoutTime(Long staffId, Device device) {
        Page<StaffLogoutLogDO> page = page(
                new Page<>(1, 1),
                Wrappers.lambdaQuery(StaffLogoutLogDO.builder().staffId(staffId).device(device.getType()).build()).orderByDesc(StaffLogoutLogDO::getId)
        );
        Date lastLogoutTime = null;
        if (Objects.nonNull(page) && !CollectionUtils.isEmpty(page.getRecords())) {
            lastLogoutTime = page.getRecords().get(0).getLogoutTime();
        }
        return lastLogoutTime;
    }
}
