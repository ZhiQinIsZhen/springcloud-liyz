package com.liyz.cloud.common.controller.util;

import com.liyz.cloud.common.base.remote.LoginInfoService;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import org.springframework.stereotype.Service;

/**
 * 注释: 重token获取 userInfoBO 安全类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 17:54
 */
@Service
public class LoginInfoUtil implements LoginInfoService {

    private ThreadLocal<JwtUserBO> userBOContainer = new ThreadLocal<>();

    /**
     * 获取登录user
     *
     * @return JwtUserBO
     */
    @Override
    public JwtUserBO getUser() {
        return userBOContainer.get();
    }

    /**
     * 设置登录user
     *
     * @param user JwtUserBO
     */
    @Override
    public void setUser(JwtUserBO user) {
        this.userBOContainer.set(user);
    }
}
