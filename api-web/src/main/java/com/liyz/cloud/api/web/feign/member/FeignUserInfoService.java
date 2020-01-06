package com.liyz.cloud.api.web.feign.member;

import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.model.bo.member.LoginUserInfoBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/6 13:37
 */
@FeignClient("member-service")
public interface FeignUserInfoService {

    @GetMapping(value = "/member/getByLoginName")
    JwtUserBO getByLoginName(@NotBlank LoginUserInfoBO loginUserInfoBO);

    @PostMapping(value = "/member/kickDownLine", consumes = "application/json")
    Date kickDownLine(@RequestBody LoginUserInfoBO downLineBO);
}
