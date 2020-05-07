package com.liyz.cloud.api.web.feign.test;

import com.liyz.cloud.common.base.Result.PageResult;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.model.bo.page.PageBaseBO;
import com.liyz.cloud.common.model.bo.sharding.UserBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/5/7 14:39
 */
@FeignClient(value = "service-sharding")
@RequestMapping("/sharding")
public interface FeignUserService {

    @GetMapping("/users")
    Result<List<UserBO>> list();

    @GetMapping("/user/{id}")
    Result<UserBO> get(@PathVariable Long id);

    @GetMapping("/user/query")
    Result<UserBO> get(String name);

    @GetMapping("/users/page")
    PageResult<UserBO> page(PageBaseBO pageBaseBO);
}
