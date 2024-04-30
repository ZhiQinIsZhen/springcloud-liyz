package com.liyz.cloud.service.staff.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyz.cloud.common.base.constant.LoginType;
import com.liyz.cloud.service.staff.dao.StaffAuthMobileMapper;
import com.liyz.cloud.service.staff.model.StaffAuthMobileDO;
import com.liyz.cloud.service.staff.model.base.StaffAuthBaseDO;
import com.liyz.cloud.service.staff.service.StaffAuthMobileService;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/15 14:04
 */
@Service
public class StaffAuthMobileServiceImpl extends ServiceImpl<StaffAuthMobileMapper, StaffAuthMobileDO> implements StaffAuthMobileService {

    @Override
    public StaffAuthBaseDO getByUsername(String username) {
        return getOne(Wrappers.lambdaQuery(StaffAuthMobileDO.builder().mobile(username).build()));
    }

    @Override
    public LoginType loginType() {
        return LoginType.MOBILE;
    }
}
