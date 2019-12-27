package com.liyz.cloud.common.security.core;

import com.liyz.cloud.common.base.remote.RemoteJwtUserService;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.security.util.JwtAuthenticationUtil;
import com.liyz.cloud.common.security.util.LoginInfoUtil;
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
    private LoginInfoUtil loginInfoUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUserBO jwtUserBO = remoteJwtUserService.getByLoginName(username);
        if (Objects.isNull(jwtUserBO)) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        loginInfoUtil.setUser(jwtUserBO);
        return JwtAuthenticationUtil.create(jwtUserBO);
    }
}
