package com.liyz.cloud.service.member.controller;

import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.service.member.remote.RemoteUserInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/getByLoginName")
    public JwtUserBO getByLoginName(String loginName) {
        return remoteUserInfoService.getByLoginName(loginName);
    }
}
