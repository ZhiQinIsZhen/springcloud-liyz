package com.liyz.cloud.service.es.remote;

import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.base.util.PageImplUtil;
import com.liyz.cloud.common.model.bo.elasticsearch.RiskConsensusBO;
import com.liyz.cloud.common.model.bo.elasticsearch.RiskConsensusPageQueryBO;
import com.liyz.cloud.common.model.bo.page.PageBaseBO;
import com.liyz.cloud.service.es.model.EsRiskConsensusDO;
import com.liyz.cloud.service.es.service.RiskConsensusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/16 16:31
 */
@Slf4j
@Service
public class RemoteRiskConsensusService {

    @Autowired
    RiskConsensusService riskConsensusService;

    public int save(RiskConsensusBO riskConsensusBO) {
        return riskConsensusService.save(CommonConverterUtil.beanCopy(riskConsensusBO, EsRiskConsensusDO.class));
    }

    public int save(List<RiskConsensusBO> list) {
        return riskConsensusService.save(CommonConverterUtil.ListTransform(list, EsRiskConsensusDO.class));
    }

    public void delete(Long id) {
        riskConsensusService.delete(id);
    }

    public void delete(List<Long> ids) {
        riskConsensusService.delete(ids);
    }

    public PageImpl<RiskConsensusBO> search(PageBaseBO pageBaseBO) {
        Page<EsRiskConsensusDO> doPage = riskConsensusService.search(pageBaseBO);
        PageImpl<EsRiskConsensusDO> implDoPage = PageImplUtil.toPageImpl(doPage);
        return CommonConverterUtil.PageTransform(implDoPage, RiskConsensusBO.class);
    }

    public PageImpl<RiskConsensusBO> search(RiskConsensusPageQueryBO queryBO) {
        Page<EsRiskConsensusDO> doPage = riskConsensusService.search(queryBO);
        PageImpl<EsRiskConsensusDO> implDoPage = PageImplUtil.toPageImpl(doPage);
        return CommonConverterUtil.PageTransform(implDoPage, RiskConsensusBO.class);
    }

    public PageImpl<RiskConsensusBO> searchForHighlight(RiskConsensusPageQueryBO queryBO) {
        Page<EsRiskConsensusDO> doPage = riskConsensusService.searchForHighlight(queryBO);
        PageImpl<EsRiskConsensusDO> implDoPage = PageImplUtil.toPageImpl(doPage);
        return CommonConverterUtil.PageTransform(implDoPage, RiskConsensusBO.class);
    }

    public Map<String,Object> aggregateForSentimentType(RiskConsensusPageQueryBO queryBO) {
        return riskConsensusService.aggregateForSentimentType(queryBO);
    }
}
