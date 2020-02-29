package com.liyz.cloud.service.es.remote;

import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.base.util.PageImplUtil;
import com.liyz.cloud.common.model.bo.elasticsearch.NearByBO;
import com.liyz.cloud.common.model.bo.page.PageBaseBO;
import com.liyz.cloud.service.es.model.NearByDO;
import com.liyz.cloud.service.es.service.NearByService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/2/29 16:02
 */
@Slf4j
@Service
public class RemoteNearByService {

    @Autowired
    NearByService nearByService;

    public PageImpl<NearByBO> search(PageBaseBO pageBaseBO) {
        Page<NearByDO> doPage = nearByService.search(pageBaseBO);
        PageImpl<NearByDO> implDoPage = PageImplUtil.toPageImpl(doPage);
        return CommonConverterUtil.PageTransform(implDoPage, NearByBO.class);
    }
}
