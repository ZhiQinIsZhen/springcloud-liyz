package com.liyz.cloud.api.web.controller.business;

import com.github.pagehelper.PageInfo;
import com.liyz.cloud.api.web.dto.page.PageBaseDTO;
import com.liyz.cloud.api.web.feign.member.FeignUserInfoService;
import com.liyz.cloud.api.web.vo.business.UserInfoVO;
import com.liyz.cloud.common.base.Result.PageResult;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.controller.annotation.LoginUser;
import com.liyz.cloud.common.model.bo.member.UserInfoBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "获取登陆的用户信息", notes = "获取登陆的用户信息")
    @GetMapping("/info")
    public Result<UserInfoVO> info(@LoginUser JwtUserBO jwtUserBO) {
        return Result.success(CommonConverterUtil.beanCopy(jwtUserBO, UserInfoVO.class));
    }

    @GetMapping("/id")
    public Result<Long> id(@LoginUser JwtUserBO jwtUserBO) {
        return Result.success(Objects.isNull(jwtUserBO) ? null : jwtUserBO.getUserId());
    }

    @GetMapping("/page")
    public PageResult<UserInfoVO> page(@Validated({PageBaseDTO.Page.class}) PageBaseDTO pageBaseDTO) {
        if (Objects.isNull(pageBaseDTO)) {
            pageBaseDTO = new PageBaseDTO();
        }
        PageInfo<UserInfoBO> boPageInfo = feignUserInfoService.pageList(pageBaseDTO.getPageNum(), pageBaseDTO.getPageSize());
        PageInfo<UserInfoVO> voPageInfo = CommonConverterUtil.PageTransform(boPageInfo, UserInfoVO.class);
        return PageResult.success(voPageInfo);
    }
}
