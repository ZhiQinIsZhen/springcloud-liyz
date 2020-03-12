package com.liyz.cloud.service.member.fegin;

import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.model.bo.sms.EmailMessageBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/12 12:05
 */
@FeignClient("sms-service")
public interface FeignSendMsgService {

    @PostMapping(value = "/email", consumes = "application/json")
    Result<Boolean> email(@RequestBody EmailMessageBO emailMessageBO);
}
