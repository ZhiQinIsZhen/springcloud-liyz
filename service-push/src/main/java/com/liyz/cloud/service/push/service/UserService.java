package com.liyz.cloud.service.push.service;

import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.service.push.feign.FeignUserInfoService;
import com.liyz.cloud.service.push.model.bo.user.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 注释:鉴权
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 10:46
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    FeignUserInfoService feignUserInfoService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    public long getUserId(LoginRequest loginRequest) {
        if (!Objects.isNull(loginRequest)) {
            if (StringUtils.isNoneBlank(loginRequest.getToken())) {
               String token = tokenHead + loginRequest.getToken();
                Result<Long> result =  feignUserInfoService.id(token);
                if (CommonCodeEnum.success.getCode().equals(result.getCode())) {
                    Long id = result.getData();
                    if (Objects.nonNull(id)) {
                        return id;
                    }
                }
            }
        }
        return -1L;
    }
}
