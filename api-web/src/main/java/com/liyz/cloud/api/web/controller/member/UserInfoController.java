package com.liyz.cloud.api.web.controller.member;

import com.liyz.cloud.api.web.dto.page.PageBaseDTO;
import com.liyz.cloud.api.web.feign.member.FeignUserInfoService;
import com.liyz.cloud.api.web.vo.member.UserInfoVO;
import com.liyz.cloud.common.base.Result.PageResult;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.controller.annotation.LoginUser;
import com.liyz.cloud.common.model.bo.member.UserInfoBO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Objects;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/6 14:31
 */
@Api(value = "用户信息", tags = "用户信息")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 401, message = "暂无授权"),
        @ApiResponse(code = 404, message = "找不到资源"),
        @ApiResponse(code = 500, message = "服务器内部错误")
})
@Slf4j
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    FeignUserInfoService feignUserInfoService;

    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String",
            paramType = "header")
    @ApiOperation(value = "获取登陆的用户信息", notes = "获取登陆的用户信息")
    @GetMapping("/info")
    public Result<UserInfoVO> info(@ApiIgnore @LoginUser JwtUserBO jwtUserBO) {
        return Result.success(CommonConverterUtil.beanCopy(jwtUserBO, UserInfoVO.class));
    }

    @ApiOperation(value = "获取登陆的用户ID", notes = "获取登陆的用户ID")
    @GetMapping("/id")
    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String",
            paramType = "header")
    public Result<Long> id(@ApiIgnore @LoginUser JwtUserBO jwtUserBO) {
        return Result.success(Objects.isNull(jwtUserBO) ? null : jwtUserBO.getUserId());
    }

    @ApiOperation(value = "分页查询用户信息", notes = "分页查询用户信息")
    @GetMapping("/page")
    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String",
            paramType = "header")
    public PageResult<UserInfoVO> page(@Validated({PageBaseDTO.Page.class}) PageBaseDTO pageBaseDTO) {
        if (Objects.isNull(pageBaseDTO)) {
            pageBaseDTO = new PageBaseDTO();
        }
        PageResult<UserInfoBO> boPageInfo = feignUserInfoService.pageList(pageBaseDTO.getPageNum(), pageBaseDTO.getPageSize());
        return CommonConverterUtil.PageTransform(boPageInfo, UserInfoVO.class);
    }
}
