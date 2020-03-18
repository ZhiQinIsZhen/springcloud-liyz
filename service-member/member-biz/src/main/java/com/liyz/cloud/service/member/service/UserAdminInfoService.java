package com.liyz.cloud.service.member.service;

import com.liyz.cloud.common.dao.service.AbstractService;
import com.liyz.cloud.service.member.model.UserAdminInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/18 17:14
 */
@Slf4j
@Service
public class UserAdminInfoService extends AbstractService<UserAdminInfoDO> {

    public UserAdminInfoDO getByLoginName(String loginName) {
        UserAdminInfoDO param = new UserAdminInfoDO();
        param.setLoginName(loginName);
        return super.getOne(param);
    }

    public int selectCount(UserAdminInfoDO userAdminInfoDO) {
        return mapper.selectCount(userAdminInfoDO);
    }

    public int loginNameCount(String loginName) {
        UserAdminInfoDO userAdminInfoDO = new UserAdminInfoDO();
        userAdminInfoDO.setLoginName(loginName);
        return mapper.selectCount(userAdminInfoDO);
    }
}
