package com.liyz.cloud.service.staff.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyz.cloud.service.staff.dao.StaffInfoMapper;
import com.liyz.cloud.service.staff.model.StaffInfoDO;
import com.liyz.cloud.service.staff.service.StaffInfoService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/15 14:03
 */
@Service
public class StaffInfoServiceImpl extends ServiceImpl<StaffInfoMapper, StaffInfoDO> implements StaffInfoService {

    @Override
    @Cacheable(cacheNames = {"auth"}, key = "'staffInfo:' + #p0", unless = "#result == null")
    public StaffInfoDO getById(Serializable id) {
        return super.getById(id);
    }
}
