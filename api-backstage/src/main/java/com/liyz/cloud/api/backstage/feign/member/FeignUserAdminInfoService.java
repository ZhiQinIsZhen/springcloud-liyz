package com.liyz.cloud.api.backstage.feign.member;

import com.liyz.cloud.common.base.Result.PageResult;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.model.bo.member.LoginUserInfoBO;
import com.liyz.cloud.common.model.bo.member.UserAdminInfoBO;
import com.liyz.cloud.common.model.bo.member.UserRegisterBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/6 13:37
 */
@FeignClient(value = "service-member", contextId = "UserAdminInfo")
@RequestMapping("/member/back")
public interface FeignUserAdminInfoService {

    @PostMapping(value = "/member/register", consumes = "application/json")
    Result<UserAdminInfoBO> register(@NotBlank UserRegisterBO userRegisterBO);

    @GetMapping(value = "/getByLoginName")
    Result<JwtUserBO> getByLoginName(@NotBlank LoginUserInfoBO loginUserInfoBO);

    @PostMapping(value = "/kickDownLine", consumes = "application/json")
    Result<Date> kickDownLine(@RequestBody LoginUserInfoBO downLineBO);

    @PostMapping(value = "/loginTime", consumes = "application/json")
    Result<Date> loginTime(@RequestBody LoginUserInfoBO downLineBO);

    @GetMapping("/getByUserId")
    Result<UserAdminInfoBO> getByUserId(@RequestParam(value = "userId", required = false) Long userId);

    @GetMapping("/page")
    PageResult<UserAdminInfoBO> pageList(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") Integer size);
}
