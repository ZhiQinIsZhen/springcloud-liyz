package com.liyz.cloud.service.member.controller;

import com.github.pagehelper.PageInfo;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.model.bo.member.LoginUserInfoBO;
import com.liyz.cloud.common.model.bo.member.UserInfoBO;
import com.liyz.cloud.service.member.remote.RemoteUserInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@RequestMapping("/member")
public class FeignUserInfoController {

    @Autowired
    RemoteUserInfoService remoteUserInfoService;

    @PostMapping(value = "/getByLoginName", consumes = "application/json")
    public JwtUserBO getByLoginName(@Validated(LoginUserInfoBO.Auth.class) @RequestBody LoginUserInfoBO loginUserInfoBO) {
        return remoteUserInfoService.getByLoginName(loginUserInfoBO.getLoginName());
    }

    @PostMapping(value = "/kickDownLine", consumes = "application/json")
    public Date kickDownLine(@Validated(LoginUserInfoBO.KickDown.class) @RequestBody LoginUserInfoBO downLineBO) {
        return remoteUserInfoService.kickDownLine(downLineBO.getUserId(), downLineBO.getDeviceEnum());
    }

    @GetMapping("/getByUserId")
    public UserInfoBO getByUserId(@RequestParam(required = false) Long userId) {
        if (Objects.isNull(userId)) {
            return null;
        }
        return remoteUserInfoService.getByUserId(userId);
    }

    @GetMapping("/page")
    public PageInfo<UserInfoBO> pageList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                         @RequestParam(required = false, defaultValue = "10") Integer size) {
        return remoteUserInfoService.pageList(page, size);
    }
}
