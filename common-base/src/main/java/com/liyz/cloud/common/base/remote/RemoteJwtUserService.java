package com.liyz.cloud.common.base.remote;

import com.liyz.cloud.common.base.remote.bo.JwtUserBO;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/11/20 9:52
 */
public interface RemoteJwtUserService {

    JwtUserBO getByLoginName(String loginName);
}
