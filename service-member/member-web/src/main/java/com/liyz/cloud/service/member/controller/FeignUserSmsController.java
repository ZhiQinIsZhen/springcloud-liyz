package com.liyz.cloud.service.member.controller;

import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.model.bo.member.ImageBO;
import com.liyz.cloud.common.model.bo.member.SmsInfoBO;
import com.liyz.cloud.service.member.remote.RemoteUserSmsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/12 13:05
 */
@Api(value = "验证码", tags = "验证码")
@RestController
@RequestMapping("/member/sms")
public class FeignUserSmsController {

    @Autowired
    RemoteUserSmsService remoteUserSmsService;

    @PostMapping(value = "/message", consumes = "application/json")
    public Result<Boolean> message(@Validated(SmsInfoBO.Sms.class) @RequestBody SmsInfoBO smsInfoBO) {
        boolean result = remoteUserSmsService.message(smsInfoBO);
        return Result.success(result);
    }

    @GetMapping(value = "/imageCode")
    public Result<ImageBO> imageCode() throws NoSuchAlgorithmException, IOException {
        ImageBO imageBO = remoteUserSmsService.imageCode();
        return Result.success(imageBO);
    }
}
