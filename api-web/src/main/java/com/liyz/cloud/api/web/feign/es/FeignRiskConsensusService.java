package com.liyz.cloud.api.web.feign.es;

import com.liyz.cloud.common.base.Result.PageResult;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.model.bo.elasticsearch.RiskConsensusBO;
import com.liyz.cloud.common.model.bo.elasticsearch.RiskConsensusPageQueryBO;
import com.liyz.cloud.common.model.bo.page.PageBaseBO;
import org.springframework.cloud.openfeign.FeignClient;
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
@FeignClient("service-elasticsearch")
public interface FeignRiskConsensusService {

    @PostMapping(value = "/es/yq/save", consumes = "application/json")
    Result<Integer> save(@RequestBody RiskConsensusBO riskConsensusBO);

    @PostMapping(value = "/es/yq/saveList", consumes = "application/json")
    Result<Integer> saveList(@RequestBody List<RiskConsensusBO> list);

    @PostMapping(value = "/es/yq/deleteById", consumes = "application/json")
    Result deleteById(@RequestBody RiskConsensusBO riskConsensusBO);

    @PostMapping(value = "/es/yq/deleteByIds", consumes = "application/json")
    Result deleteByIds(@RequestBody List<RiskConsensusBO> list);

    @PostMapping(value = "/es/yq/search", consumes = "application/json")
    PageResult<RiskConsensusBO> search(@RequestBody PageBaseBO pageBaseBO);

    @PostMapping(value = "/es/yq/searchForCondition", consumes = "application/json")
    PageResult<RiskConsensusBO> searchForCondition(RiskConsensusPageQueryBO queryBO);

    @PostMapping(value = "/es/yq/searchForHighlight", consumes = "application/json")
    PageResult<RiskConsensusBO> searchForHighlight(RiskConsensusPageQueryBO queryBO);

    @PostMapping(value = "/es/yq/aggregateForSentimentType", consumes = "application/json")
    Result<Map<String,Object>> aggregateForSentimentType(RiskConsensusPageQueryBO queryBO);
}
