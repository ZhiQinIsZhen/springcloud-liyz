package com.liyz.cloud.service.sso.service;

import com.alibaba.fastjson.JSON;
import com.liyz.cloud.service.sso.config.MyUser;
import com.liyz.cloud.service.sso.model.UserInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/3 23:57
 */
@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoDO userInfoDO = userInfoService.getBy("login_name", username);
        if (Objects.isNull(userInfoDO)) {
            log.warn("用户{}不存在", username);
            throw new UsernameNotFoundException(username);
        }
        MyUser myUser = new MyUser(userInfoDO.getLoginName(), passwordEncoder.encode(userInfoDO.getLoginPwd()), null);
        log.info("登录成功！用户: {}", JSON.toJSONString(myUser));
        return myUser;
    }
}
