package com.liyz.cloud.common.backsecurity.core;

import com.liyz.cloud.common.backsecurity.util.JwtAuthenticationUtil;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.common.base.remote.LoginInfoService;
import com.liyz.cloud.common.base.remote.RemoteJwtUserService;
import com.liyz.cloud.common.base.remote.RemoteResourcesService;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.base.remote.bo.ResourcesBO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 17:50
 */
@Slf4j
@AllArgsConstructor
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private RemoteJwtUserService remoteJwtUserService;
    private LoginInfoService loginInfoService;
    private RemoteResourcesService remoteResourcesService;

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
        List<ResourcesBO> boList = remoteResourcesService.list(jwtUserBO.getUserId());
        int size = CollectionUtils.isEmpty(boList) ? 10 : boList.size();
        List<GrantedAuthority> authorities = new ArrayList<>(size);
        boList.forEach(resourcesBO -> {
            GrantedAuthority grantedAuthority = new JwtGrantedAuthority(resourcesBO.getUrl(),
                    resourcesBO.getMethod());
            authorities.add(grantedAuthority);
        });
        return JwtAuthenticationUtil.create(jwtUserBO, authorities);
    }
}
