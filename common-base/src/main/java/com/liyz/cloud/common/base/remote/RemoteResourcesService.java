package com.liyz.cloud.common.base.remote;

import com.liyz.cloud.common.base.remote.bo.ResourcesBO;

import java.util.List;

/**
 * 注释:管理后台需要实现该接口并且注入成bean
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/18 16:30
 */
public interface RemoteResourcesService {

    List<ResourcesBO> list(Long userId);
}
