package com.liyz.cloud.service.sso.service;

import com.alibaba.fastjson.JSON;
import com.liyz.cloud.common.base.remote.LoginInfoService;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.service.sso.model.UserInfoDO;
import com.liyz.cloud.service.sso.util.JwtAuthenticationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private UserInfoService userInfoService;
    @Autowired
    private LoginInfoService loginInfoService;

    /**
     * 授权的时候是对角色授权，而认证的时候应该基于资源，而不是角色，因为资源是不变的，而用户的角色是会变的
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoDO param = new UserInfoDO();
        param.setLoginName(username);
        UserInfoDO userInfoDO = userInfoService.getOne(param);
        if (Objects.isNull(userInfoDO)) {
            log.warn("用户{}不存在", username);
            throw new UsernameNotFoundException(username);
        }
        /*//设置用户角色 -- 暂时不设置
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (StringUtils.isNotBlank(userInfoDO.getRoles())) {
            String[] roles = userInfoDO.getRoles().split(",");
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }*/
        JwtUserBO jwtUserBO = CommonConverterUtil.beanCopy(userInfoDO, JwtUserBO.class);
        loginInfoService.setUser(jwtUserBO);
        log.info("登录成功！用户: {}", JSON.toJSONString(jwtUserBO));
        return JwtAuthenticationUtil.create(jwtUserBO);
    }
}
