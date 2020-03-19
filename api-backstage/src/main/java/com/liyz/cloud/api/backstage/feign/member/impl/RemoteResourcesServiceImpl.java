package com.liyz.cloud.api.backstage.feign.member.impl;

import com.liyz.cloud.common.base.remote.RemoteResourcesService;
import com.liyz.cloud.common.base.remote.bo.ResourcesBO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/18 18:08
 */
@Service
public class RemoteResourcesServiceImpl implements RemoteResourcesService {

    /**
     * 自己实现公司、角色、以及临时权限功能，然后进行汇总
     *
     * @param userId
     * @return
     */
    @Override
    public List<ResourcesBO> list(Long userId) {
        return null;
    }
}
