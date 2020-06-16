package com.liyz.cloud.api.web.feign.es;

import com.liyz.cloud.common.base.Result.PageResult;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.model.bo.elasticsearch.JudgementResultBO;
import com.liyz.cloud.common.model.bo.elasticsearch.JudgementResultPageQueryBO;
import com.liyz.cloud.common.model.bo.page.PageBaseBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/15 16:30
 */
@RequestMapping("/es/fs")
@FeignClient(value = "service-elasticsearch", contextId = "JudgementResult")
public interface FeignJudgementResultService {

    @PostMapping(value = "/save", consumes = "application/json")
    Result<Integer> save(@RequestBody JudgementResultBO judgementResultBO);

    @PostMapping(value = "/saveList", consumes = "application/json")
    Result<Integer> saveList(@RequestBody List<JudgementResultBO> list);

    @PostMapping(value = "/deleteById", consumes = "application/json")
    Result deleteById(@RequestBody JudgementResultBO judgementResultBO);

    @PostMapping(value = "/deleteByIds", consumes = "application/json")
    Result deleteByIds(@RequestBody List<JudgementResultBO> list);

    @PostMapping(value = "/search", consumes = "application/json")
    PageResult<JudgementResultBO> search(@RequestBody PageBaseBO pageBaseBO);

    @PostMapping(value = "/searchForCondition", consumes = "application/json")
    PageResult<JudgementResultBO> searchForCondition(JudgementResultPageQueryBO queryBO);
}
