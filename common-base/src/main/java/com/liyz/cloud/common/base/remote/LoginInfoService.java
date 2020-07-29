package com.liyz.cloud.common.base.remote;

import com.liyz.cloud.common.base.remote.bo.JwtUserBO;

/**
 * 注释:用户登陆信息获取接口
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/6 14:23
 */
public interface LoginInfoService {

    JwtUserBO getUser();

    void setUser(JwtUserBO user);
}
