package com.liyz.cloud.service.member.service;

import com.liyz.cloud.common.dao.service.AbstractService;
import com.liyz.cloud.service.member.model.UserInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 15:44
 */
@Slf4j
@Service
public class UserInfoService extends AbstractService<UserInfoDO> {

    @Override
    public UserInfoDO getById(Object id) {
        return super.getById(id);
    }

    public int selectCount(UserInfoDO userInfoDO) {
        return mapper.selectCount(userInfoDO);
    }

    public int loginNameCount(String loginName) {
        UserInfoDO userInfoDO = new UserInfoDO();
        userInfoDO.setLoginName(loginName);
        return mapper.selectCount(userInfoDO);
    }
}
