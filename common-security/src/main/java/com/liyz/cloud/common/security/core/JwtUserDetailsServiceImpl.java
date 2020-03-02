package com.liyz.cloud.common.security.core;

import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.common.base.remote.LoginInfoService;
import com.liyz.cloud.common.base.remote.RemoteJwtUserService;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.security.util.JwtAuthenticationUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 17:50
 */
@AllArgsConstructor
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private RemoteJwtUserService remoteJwtUserService;
    private LoginInfoService loginInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result<JwtUserBO> result = remoteJwtUserService.getByLoginName(username);
        JwtUserBO jwtUserBO = null;
        if (CommonCodeEnum.success.getCode().equals(result.getCode())) {
            jwtUserBO = result.getData();
        }
        if (Objects.isNull(jwtUserBO)) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        loginInfoService.setUser(jwtUserBO);
        return JwtAuthenticationUtil.create(jwtUserBO);
    }
}
