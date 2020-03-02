package com.liyz.cloud.service.es.controller;

import com.liyz.cloud.common.base.Result.PageResult;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.model.bo.elasticsearch.RiskConsensusBO;
import com.liyz.cloud.common.model.bo.elasticsearch.RiskConsensusPageQueryBO;
import com.liyz.cloud.common.model.bo.page.PageBaseBO;
import com.liyz.cloud.service.es.remote.RemoteRiskConsensusService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/16 18:10
 */
@Api(value = "舆情信息", tags = "舆情信息")
@RestController
@RequestMapping("/es/yq")
public class FeignRiskConsensusController {

    @Autowired
    RemoteRiskConsensusService remoteRiskConsensusService;

    @PostMapping(value = "/save", consumes = "application/json")
    public Result<Integer> save(@RequestBody RiskConsensusBO riskConsensusBO) {
        int count = remoteRiskConsensusService.save(riskConsensusBO);
        return Result.success(count);
    }

    @PostMapping(value = "/saveList", consumes = "application/json")
    public int saveList(@RequestBody List<RiskConsensusBO> list) {
        return remoteRiskConsensusService.save(list);
    }

    @PostMapping(value = "/deleteById", consumes = "application/json")
    public void deleteById(@RequestBody RiskConsensusBO riskConsensusBO) {
        remoteRiskConsensusService.delete(riskConsensusBO.getId());
    }

    @PostMapping(value = "/deleteByIds", consumes = "application/json")
    public void deleteByIds(@RequestBody List<RiskConsensusBO> list) {
        remoteRiskConsensusService.delete(list.stream().map(RiskConsensusBO::getId).collect(Collectors.toList()));
    }

    @PostMapping(value = "/search", consumes = "application/json")
    public PageResult<RiskConsensusBO> search(PageBaseBO pageBaseBO) {
        PageImpl<RiskConsensusBO> implBoPage = remoteRiskConsensusService.search(pageBaseBO);
        return PageResult.success(implBoPage);
    }

    @GetMapping(value = "/searchForCondition")
    public PageResult<RiskConsensusBO> searchForCondition(RiskConsensusPageQueryBO queryBO) {
        PageImpl<RiskConsensusBO> implBoPage = remoteRiskConsensusService.search(queryBO);
        return PageResult.success(implBoPage);
    }

    @GetMapping(value = "/searchForHighlight")
    public PageResult<RiskConsensusBO> searchForHighlight(RiskConsensusPageQueryBO queryBO) {
        PageImpl<RiskConsensusBO> implBoPage = remoteRiskConsensusService.searchForHighlight(queryBO);
        return PageResult.success(implBoPage);
    }

    @GetMapping(value = "/aggregateForSentimentType")
    public Result<Map<String,Object>> aggregateForSentimentType(RiskConsensusPageQueryBO queryBO) {
        return Result.success(remoteRiskConsensusService.aggregateForSentimentType(queryBO));
    }
}
