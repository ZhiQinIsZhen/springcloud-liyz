package com.liyz.cloud.service.push.feign;

import com.liyz.cloud.common.base.Result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/5 10:01
 */
@FeignClient("api-web")
public interface FeignUserInfoService {

    @GetMapping("/user/id")
    Result<Long> id(@RequestHeader(name = "Authorization") String token);
}
