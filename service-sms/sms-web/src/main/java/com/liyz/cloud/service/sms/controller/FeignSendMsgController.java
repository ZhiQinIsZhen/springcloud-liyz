package com.liyz.cloud.service.sms.controller;

import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.model.bo.sms.EmailMessageBO;
import com.liyz.cloud.service.sms.remote.RemoteSendMsgService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/11 17:15
 */
@Api(value = "信息发送", tags = "信息发送")
@RestController
@RequestMapping("/sms")
public class FeignSendMsgController {

    @Autowired
    RemoteSendMsgService remoteSendMsgService;

    @PostMapping(value = "/email", consumes = "application/json")
    public Result<Boolean> email(@Validated(EmailMessageBO.Email.class) @RequestBody EmailMessageBO emailMessageBO) {
        remoteSendMsgService.email(emailMessageBO);
        return Result.success(Boolean.TRUE);
    }
}
