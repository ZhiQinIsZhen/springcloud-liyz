package com.liyz.cloud.api.web.controller.test;

import com.liyz.cloud.api.web.feign.test.FeignUserService;
import com.liyz.cloud.common.base.Result.PageResult;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.model.bo.page.PageBaseBO;
import com.liyz.cloud.common.model.bo.sharding.UserBO;
import com.liyz.cloud.common.security.annotation.Anonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/5/7 14:44
 */
@Api(value = "用户-sharding-test", tags = "用户-sharding-test")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 401, message = "暂无授权"),
        @ApiResponse(code = 404, message = "找不到资源"),
        @ApiResponse(code = 500, message = "服务器内部错误")
})
@Slf4j
@RestController
@RequestMapping("/test/sharding")
public class UserServiceController {

    @Autowired
    FeignUserService feignUserService;

    @ApiOperation(value = "用户列表", notes = "用户列表")
    @Anonymous
    @GetMapping("/users")
    public Result<List<UserBO>> list() {
        return feignUserService.list();
    }

    @ApiOperation(value = "用户id信息", notes = "用户id信息")
    @Anonymous
    @GetMapping("/users/{id}")
    public Result<UserBO> get(@PathVariable Long id) {
        return feignUserService.get(id);
    }

    @ApiOperation(value = "用户名称信息", notes = "用户名称信息")
    @Anonymous
    @GetMapping("/users/query")
    public Result<UserBO> get(String name) {
        return feignUserService.get(name);
    }

    @ApiOperation(value = "用户名称信息", notes = "用户名称信息")
    @Anonymous
    @GetMapping("/users/page")
    public PageResult<UserBO> page(PageBaseBO pageBaseBO) {
        return feignUserService.page(pageBaseBO);
    }
}
