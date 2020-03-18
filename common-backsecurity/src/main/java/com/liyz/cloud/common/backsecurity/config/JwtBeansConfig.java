package com.liyz.cloud.common.backsecurity.config;

import com.liyz.cloud.common.backsecurity.core.JwtUserDetailsServiceImpl;
import com.liyz.cloud.common.base.remote.LoginInfoService;
import com.liyz.cloud.common.base.remote.RemoteJwtUserService;
import com.liyz.cloud.common.base.remote.RemoteResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 注释: jwt bean 初始化
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 17:44
 */
@Configuration
public class JwtBeansConfig {

    @Autowired
    private RemoteJwtUserService remoteJwtUserService;
    @Autowired
    private LoginInfoService loginInfoService;
    @Autowired
    private RemoteResourcesService remoteResourcesService;

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new JwtUserDetailsServiceImpl(remoteJwtUserService, loginInfoService, remoteResourcesService);
    }
}
