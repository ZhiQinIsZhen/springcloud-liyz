package com.liyz.cloud.service.es.controller;

import com.liyz.cloud.common.base.Result.PageResult;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.model.bo.elasticsearch.JudgementResultBO;
import com.liyz.cloud.common.model.bo.elasticsearch.JudgementResultPageQueryBO;
import com.liyz.cloud.common.model.bo.page.PageBaseBO;
import com.liyz.cloud.service.es.remote.RemoteJudgementResultService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/15 16:26
 */
@Api(value = "法诉信息", tags = "法诉信息")
@RestController
@RequestMapping("/es/fs")
public class FeignJudgementResultController {

    @Autowired
    RemoteJudgementResultService remoteJudgementResultService;

    @PostMapping(value = "/save", consumes = "application/json")
    public Result<Integer> save(@RequestBody JudgementResultBO judgementResultBO) {
        int count = remoteJudgementResultService.save(judgementResultBO);
        return Result.success(count);
    }

    @PostMapping(value = "/saveList", consumes = "application/json")
    public Result<Integer> saveList(@RequestBody List<JudgementResultBO> list) {
        int count = remoteJudgementResultService.save(list);
        return Result.success(count);
    }

    @PostMapping(value = "/deleteById", consumes = "application/json")
    public Result deleteById(@RequestBody JudgementResultBO judgementResultBO) {
        remoteJudgementResultService.delete(judgementResultBO.getId());
        return Result.success();
    }

    @PostMapping(value = "/deleteByIds", consumes = "application/json")
    public Result deleteByIds(@RequestBody List<JudgementResultBO> list) {
        remoteJudgementResultService.delete(list.stream().map(JudgementResultBO::getId).collect(Collectors.toList()));
        return Result.success();
    }

    @PostMapping(value = "/search", consumes = "application/json")
    public PageResult<JudgementResultBO> search(@RequestBody PageBaseBO pageBaseBO) {
        PageImpl<JudgementResultBO> implBoPage = remoteJudgementResultService.search(pageBaseBO);
        return PageResult.success(implBoPage);
    }

    @PostMapping(value = "/searchForCondition", consumes = "application/json")
    public PageResult<JudgementResultBO> searchForCondition(@RequestBody JudgementResultPageQueryBO queryBO) {
        PageImpl<JudgementResultBO> implBoPage = remoteJudgementResultService.search(queryBO);
        return PageResult.success(implBoPage);
    }
}
