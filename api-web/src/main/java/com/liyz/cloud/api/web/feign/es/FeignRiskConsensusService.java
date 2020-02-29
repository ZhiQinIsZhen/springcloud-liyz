package com.liyz.cloud.api.web.feign.es;

import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.model.bo.elasticsearch.NearByBO;
import com.liyz.cloud.common.model.bo.elasticsearch.RiskConsensusBO;
import com.liyz.cloud.common.model.bo.elasticsearch.RiskConsensusPageQueryBO;
import com.liyz.cloud.common.model.bo.page.PageBaseBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/2/29 14:56
 */
@FeignClient("elasticsearch-service")
public interface FeignRiskConsensusService {

    @PostMapping(value = "/es/yq/save", consumes = "application/json")
    Result<Integer> save(@RequestBody RiskConsensusBO riskConsensusBO);

    @PostMapping(value = "/es/yq/saveList", consumes = "application/json")
    int saveList(@RequestBody List<RiskConsensusBO> list);

    @PostMapping(value = "/es/yq/deleteById", consumes = "application/json")
    void deleteById(@RequestBody RiskConsensusBO riskConsensusBO);

    @PostMapping(value = "/es/yq/deleteByIds", consumes = "application/json")
    void deleteByIds(@RequestBody List<RiskConsensusBO> list);

    @GetMapping(value = "/es/yq/search")
    PageImpl<RiskConsensusBO> search(PageBaseBO pageBaseBO);

    @GetMapping(value = "/es/yq/search/near")
    PageImpl<NearByBO> searchNear(PageBaseBO pageBaseBO);

    @GetMapping(value = "/es/yq/searchForCondition")
    PageImpl<RiskConsensusBO> searchForCondition(RiskConsensusPageQueryBO queryBO);

    @GetMapping(value = "/es/yq/searchForHighlight")
    PageImpl<RiskConsensusBO> searchForHighlight(RiskConsensusPageQueryBO queryBO);

    @GetMapping(value = "/es/yq/aggregateForSentimentType")
    Map<String,Object> aggregateForSentimentType(RiskConsensusPageQueryBO queryBO);
}
