package com.liyz.cloud.service.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liyz.cloud.service.auth.model.AuthJwtDO;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/14 17:59
 */
public interface AuthJwtService extends IService<AuthJwtDO> {

    /**
     * 根据资源ID获取JWT配置信息
     *
     * @param clientId 资源ID
     * @return JWT配置信息
     */
    AuthJwtDO getByClientId(String clientId);
}
