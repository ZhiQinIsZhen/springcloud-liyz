package com.liyz.cloud.common.api.feign;

import com.liyz.cloud.common.api.bo.AuthJwtBO;
import com.liyz.cloud.common.api.bo.AuthUserBO;
import com.liyz.cloud.common.base.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/5/9 16:09
 */
@FeignClient(value = "cloud-service-auth", contextId = "JwtParseService", path = "/auth/jwt")
public interface FeignJwtParseService {

    @Operation(summary = "解析token")
    @GetMapping("/parseToken")
    Result<AuthUserBO> parseToken(@RequestParam("token") String token, @RequestParam("clientId") String clientId);

    @Operation(summary = "生成token")
    @PostMapping("/generateToken")
    Result<AuthJwtBO> generateToken(@RequestBody AuthUserBO authUser);

    @Operation(summary = "获取失效时间")
    @GetMapping("/getExpiration")
    Result<Long> getExpiration(@RequestParam("token") final String token);
}
