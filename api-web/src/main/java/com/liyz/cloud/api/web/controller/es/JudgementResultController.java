package com.liyz.cloud.api.web.controller.es;

import com.liyz.cloud.api.web.dto.page.PageBaseDTO;
import com.liyz.cloud.api.web.feign.es.FeignJudgementResultService;
import com.liyz.cloud.common.base.Result.PageResult;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.model.bo.elasticsearch.JudgementResultBO;
import com.liyz.cloud.common.model.bo.elasticsearch.JudgementResultPageQueryBO;
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

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/15 16:34
 */
@Api(value = "法诉信息", tags = "法诉信息")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 401, message = "暂无授权"),
        @ApiResponse(code = 404, message = "找不到资源"),
        @ApiResponse(code = 500, message = "服务器内部错误")
})
@Slf4j
@RestController
@RequestMapping("/es/judgement")
public class JudgementResultController {

    @Autowired
    FeignJudgementResultService feignJudgementResultService;

    @ApiOperation(value = "保存法诉信息", notes = "保存法诉信息")
    @Anonymous
    @PostMapping(value = "/save")
    public Result<Integer> save(@RequestBody JudgementResultBO judgementResultBO) {
        return feignJudgementResultService.save(CommonConverterUtil.beanCopy(judgementResultBO, JudgementResultBO.class));
    }

    @ApiOperation(value = "批量保存法诉信息", notes = "批量保存法诉信息")
    @Anonymous
    @PostMapping(value = "/saveList", consumes = "application/json")
    public Result<Integer> saveList(@RequestBody List<JudgementResultBO> list) {
        return feignJudgementResultService.saveList(CommonConverterUtil.ListTransform(list, JudgementResultBO.class));
    }

    @ApiOperation(value = "通过Id删除法诉信息", notes = "通过Id删除法诉信息")
    @Anonymous
    @PostMapping(value = "/deleteById")
    public Result deleteById(@RequestBody JudgementResultBO judgementResultBO) {
        feignJudgementResultService.deleteById(CommonConverterUtil.beanCopy(judgementResultBO, JudgementResultBO.class));
        return Result.success();
    }

    @ApiOperation(value = "通过Ids删除法诉信息", notes = "通过Ids删除法诉信息")
    @Anonymous
    @PostMapping(value = "/deleteByIds")
    public Result deleteByIds(@RequestBody List<JudgementResultBO> list) {
        feignJudgementResultService.deleteByIds(CommonConverterUtil.ListTransform(list, JudgementResultBO.class));
        return Result.success();
    }

    @ApiOperation(value = "分页查询法诉信息", notes = "分页查询法诉信息")
    @Anonymous
    @GetMapping(value = "/search")
    public PageResult<JudgementResultBO> search(PageBaseDTO pagebasedto) {
        PageResult<JudgementResultBO> boPage = feignJudgementResultService.search(CommonConverterUtil.beanCopy(pagebasedto, PageBaseBO.class));
        return CommonConverterUtil.PageTransform(boPage, JudgementResultBO.class);
    }

    @ApiOperation(value = "分页查询法诉信息-查询条件", notes = "分页查询法诉信息-查询条件")
    @Anonymous
    @GetMapping(value = "/searchForCondition")
    PageResult<JudgementResultBO> searchForCondition(JudgementResultPageQueryBO queryDTO) {
        PageResult<JudgementResultBO> boPage = feignJudgementResultService.searchForCondition(CommonConverterUtil.beanCopy(queryDTO, JudgementResultPageQueryBO.class));
        return CommonConverterUtil.PageTransform(boPage, JudgementResultBO.class);
    }
}
