package com.liyz.cloud.service.member.controller;

import com.github.pagehelper.PageInfo;
import com.liyz.cloud.common.base.Result.PageResult;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.model.bo.member.LoginUserInfoBO;
import com.liyz.cloud.common.model.bo.member.UserAdminInfoBO;
import com.liyz.cloud.common.model.bo.member.UserRegisterBO;
import com.liyz.cloud.service.member.remote.RemoteUserAdminInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/3 17:50
 */
@Api(value = "用户信息", tags = "用户信息")
@RestController
@RequestMapping("/member/back/")
public class FeignUserAdminInfoController {

    @Autowired
    RemoteUserAdminInfoService remoteUserAdminInfoService;

    @PostMapping(value = "/register", consumes = "application/json")
    public Result<UserAdminInfoBO> getByLoginName(@RequestBody UserRegisterBO userRegisterBO) {
        return Result.success(remoteUserAdminInfoService.register(userRegisterBO));
    }

    @PostMapping(value = "/getByLoginName", consumes = "application/json")
    public Result<JwtUserBO> getByLoginName(@RequestBody LoginUserInfoBO loginUserInfoBO) {
        return Result.success(remoteUserAdminInfoService.getByLoginName(loginUserInfoBO.getLoginName()));
    }

    @PostMapping(value = "/kickDownLine", consumes = "application/json")
    public Result<Date> kickDownLine(@RequestBody LoginUserInfoBO downLineBO) {
        return Result.success(remoteUserAdminInfoService.kickDownLine(downLineBO.getUserId()));
    }

    @PostMapping(value = "/loginTime", consumes = "application/json")
    public Result<Date> loginTime(@RequestBody LoginUserInfoBO downLineBO) {
        return Result.success(remoteUserAdminInfoService.loginTime(downLineBO.getUserId(), downLineBO.getIp()));
    }

    @GetMapping("/getByUserId")
    public Result<UserAdminInfoBO> getByUserId(@RequestParam(required = false) Long userId) {
        if (Objects.isNull(userId)) {
            return Result.success();
        }
        return Result.success(remoteUserAdminInfoService.getByUserId(userId));
    }

    @GetMapping("/page")
    public PageResult<UserAdminInfoBO> pageList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                           @RequestParam(required = false, defaultValue = "10") Integer size) {
        PageInfo<UserAdminInfoBO> pageInfo = remoteUserAdminInfoService.pageList(page, size);
        return PageResult.success(pageInfo);
    }
}
