package com.liyz.cloud.service.member.controller;

import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.model.bo.member.LoginUserInfoBO;
import com.liyz.cloud.service.member.remote.RemoteUserInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
}
