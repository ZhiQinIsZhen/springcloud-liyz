package com.liyz.cloud.common.security.config;

import com.liyz.cloud.common.base.remote.RemoteJwtUserService;
import com.liyz.cloud.common.security.core.JwtUserDetailsServiceImpl;
import com.liyz.cloud.common.security.util.LoginInfoUtil;
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
public class JwtBeansConfigurer {

    @Autowired
    RemoteJwtUserService remoteJwtUserService;

    @Autowired
    LoginInfoUtil loginInfoUtil;

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new JwtUserDetailsServiceImpl(remoteJwtUserService, loginInfoUtil);
    }
}
