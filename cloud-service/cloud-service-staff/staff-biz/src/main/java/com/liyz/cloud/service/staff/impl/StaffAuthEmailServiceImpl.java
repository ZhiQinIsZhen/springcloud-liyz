package com.liyz.cloud.service.staff.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyz.cloud.common.feign.constant.LoginType;
import com.liyz.cloud.service.staff.dao.StaffAuthEmailMapper;
import com.liyz.cloud.service.staff.model.StaffAuthEmailDO;
import com.liyz.cloud.service.staff.model.base.StaffAuthBaseDO;
import com.liyz.cloud.service.staff.service.StaffAuthEmailService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/15 14:06
 */
@Service
public class StaffAuthEmailServiceImpl extends ServiceImpl<StaffAuthEmailMapper, StaffAuthEmailDO> implements StaffAuthEmailService {

    @Override
    @Cacheable(cacheNames = {"auth"}, key = "'staffAuthEmail:' + #p0", unless = "#result == null")
    public StaffAuthBaseDO getByUsername(String username) {
        return getOne(Wrappers.lambdaQuery(StaffAuthEmailDO.builder().email(username).build()));
    }

    @Override
    public LoginType loginType() {
        return LoginType.EMAIL;
    }
}
