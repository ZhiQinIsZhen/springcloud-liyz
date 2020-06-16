package com.liyz.cloud.service.es.remote;

import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.base.util.PageImplUtil;
import com.liyz.cloud.common.model.bo.elasticsearch.JudgementResultBO;
import com.liyz.cloud.common.model.bo.elasticsearch.JudgementResultPageQueryBO;
import com.liyz.cloud.common.model.bo.page.PageBaseBO;
import com.liyz.cloud.service.es.model.JudgementResultDO;
import com.liyz.cloud.service.es.service.JudgementResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/15 16:24
 */
@Service
public class RemoteJudgementResultService {

    @Autowired
    JudgementResultService judgementResultService;

    public int save(JudgementResultBO judgementResultBO) {
        return judgementResultService.save(CommonConverterUtil.beanCopy(judgementResultBO, JudgementResultDO.class));
    }

    public int save(List<JudgementResultBO> list) {
        return judgementResultService.save(CommonConverterUtil.ListTransform(list, JudgementResultDO.class));
    }

    public void delete(String id) {
        judgementResultService.delete(id);
    }

    public void delete(List<String> ids) {
        judgementResultService.delete(ids);
    }

    public PageImpl<JudgementResultBO> search(PageBaseBO pageBaseBO) {
        Page<JudgementResultDO> doPage = judgementResultService.search(pageBaseBO);
        PageImpl<JudgementResultDO> implDoPage = PageImplUtil.toPageImpl(doPage);
        return CommonConverterUtil.PageTransform(implDoPage, JudgementResultBO.class);
    }

    public PageImpl<JudgementResultBO> search(JudgementResultPageQueryBO queryBO) {
        Page<JudgementResultDO> doPage = judgementResultService.search(queryBO);
        PageImpl<JudgementResultDO> implDoPage = PageImplUtil.toPageImpl(doPage);
        return CommonConverterUtil.PageTransform(implDoPage, JudgementResultBO.class);
    }
}
