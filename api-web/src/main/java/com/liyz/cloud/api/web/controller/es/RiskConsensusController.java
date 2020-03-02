package com.liyz.cloud.api.web.controller.es;

import com.liyz.cloud.api.web.dto.es.RiskConsensusDTO;
import com.liyz.cloud.api.web.dto.es.RiskConsensusPageQueryDTO;
import com.liyz.cloud.api.web.dto.page.PageBaseDTO;
import com.liyz.cloud.api.web.feign.es.FeignRiskConsensusService;
import com.liyz.cloud.api.web.vo.es.RiskConsensusVO;
import com.liyz.cloud.common.base.Result.PageResult;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.model.bo.elasticsearch.RiskConsensusBO;
import com.liyz.cloud.common.model.bo.elasticsearch.RiskConsensusPageQueryBO;
import com.liyz.cloud.common.model.bo.page.PageBaseBO;
import com.liyz.cloud.common.security.annotation.Anonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/2/29 14:53
 */
@Api(value = "舆情信息", tags = "舆情信息")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 401, message = "暂无授权"),
        @ApiResponse(code = 404, message = "找不到资源"),
        @ApiResponse(code = 500, message = "服务器内部错误")
})
@Slf4j
@RestController
@RequestMapping("/es/consensus")
public class RiskConsensusController {

    @Autowired
    private FeignRiskConsensusService feignRiskConsensusService;

    @ApiOperation(value = "保存舆情信息", notes = "保存舆情信息")
    @Anonymous
    @PostMapping(value = "/save")
    public Result<Integer> save(@RequestBody RiskConsensusDTO riskConsensusDTO) {
        return feignRiskConsensusService.save(CommonConverterUtil.beanCopy(riskConsensusDTO, RiskConsensusBO.class));
    }

    @ApiOperation(value = "批量保存舆情信息", notes = "批量保存舆情信息")
    @Anonymous
    @PostMapping(value = "/saveList", consumes = "application/json")
    public Result<Integer> saveList(@RequestBody List<RiskConsensusDTO> list) {
        return feignRiskConsensusService.saveList(CommonConverterUtil.ListTransform(list, RiskConsensusBO.class));
    }

    @ApiOperation(value = "通过Id删除舆情信息", notes = "通过Id删除舆情信息")
    @Anonymous
    @PostMapping(value = "/deleteById")
    public Result deleteById(@RequestBody RiskConsensusDTO riskConsensusDTO) {
        feignRiskConsensusService.deleteById(CommonConverterUtil.beanCopy(riskConsensusDTO, RiskConsensusBO.class));
        return Result.success();
    }

    @ApiOperation(value = "通过Ids删除舆情信息", notes = "通过Ids删除舆情信息")
    @Anonymous
    @PostMapping(value = "/deleteByIds")
    public Result deleteByIds(@RequestBody List<RiskConsensusDTO> list) {
        feignRiskConsensusService.deleteByIds(CommonConverterUtil.ListTransform(list, RiskConsensusBO.class));
        return Result.success();
    }

    @ApiOperation(value = "分页查询舆情信息", notes = "分页查询舆情信息")
    @Anonymous
    @GetMapping(value = "/search")
    public PageResult<RiskConsensusVO> search(PageBaseDTO pagebasedto) {
        PageResult<RiskConsensusBO> boPage = feignRiskConsensusService.search(CommonConverterUtil.beanCopy(pagebasedto, PageBaseBO.class));
        return CommonConverterUtil.PageTransform(boPage, RiskConsensusVO.class);
    }

    @ApiOperation(value = "分页查询舆情信息-查询条件", notes = "分页查询舆情信息-查询条件")
    @Anonymous
    @GetMapping(value = "/searchForCondition")
    PageResult<RiskConsensusVO> searchForCondition(RiskConsensusPageQueryDTO queryDTO) {
        PageResult<RiskConsensusBO> boPage = feignRiskConsensusService.searchForCondition(CommonConverterUtil.beanCopy(queryDTO, RiskConsensusPageQueryBO.class));
        return CommonConverterUtil.PageTransform(boPage, RiskConsensusVO.class);
    }

    @ApiOperation(value = "分页查询舆情信息-高亮", notes = "分页查询舆情信息-高亮")
    @Anonymous
    @GetMapping(value = "/searchForHighlight")
    PageResult<RiskConsensusVO> searchForHighlight(RiskConsensusPageQueryDTO queryDTO) {
        PageResult<RiskConsensusBO> boPage = feignRiskConsensusService.searchForHighlight(CommonConverterUtil.beanCopy(queryDTO, RiskConsensusPageQueryBO.class));
        return CommonConverterUtil.PageTransform(boPage, RiskConsensusVO.class);
    }

    @ApiOperation(value = "舆情聚合查询", notes = "舆情聚合查询")
    @Anonymous
    @GetMapping(value = "/aggregateForSentimentType")
    public Result<Map<String,Object>> aggregateForSentimentType(RiskConsensusPageQueryDTO queryDTO) {
        Result<Map<String,Object>> map = feignRiskConsensusService.aggregateForSentimentType(CommonConverterUtil.beanCopy(queryDTO, RiskConsensusPageQueryBO.class));
        return map;
    }
}
