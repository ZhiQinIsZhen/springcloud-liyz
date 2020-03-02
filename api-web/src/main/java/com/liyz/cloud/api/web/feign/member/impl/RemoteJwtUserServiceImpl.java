package com.liyz.cloud.api.web.feign.member.impl;

import com.liyz.cloud.api.web.feign.member.FeignUserInfoService;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.remote.RemoteJwtUserService;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.model.bo.member.LoginUserInfoBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/6 13:52
 */
@Service
public class RemoteJwtUserServiceImpl implements RemoteJwtUserService {

    @Autowired
    FeignUserInfoService feignUserInfoService;

    @Override
    public Result<JwtUserBO> getByLoginName(String loginName) {
        LoginUserInfoBO loginUserInfoBO = new LoginUserInfoBO();
        loginUserInfoBO.setLoginName(loginName);
        return feignUserInfoService.getByLoginName(loginUserInfoBO);
    }
}
