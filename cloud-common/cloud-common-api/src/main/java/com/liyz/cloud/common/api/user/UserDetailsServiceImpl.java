package com.liyz.cloud.common.api.user;

import com.google.common.base.Joiner;
import com.liyz.cloud.common.api.bo.AuthUserBO;
import com.liyz.cloud.common.api.context.AuthContext;
import com.liyz.cloud.common.base.constant.AuthExceptionCodeEnum;
import com.liyz.cloud.common.base.constant.Device;
import com.liyz.cloud.common.util.constant.CommonConstant;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/14 15:45
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private static String clientId;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        int index = username.indexOf(CommonConstant.DEFAULT_JOINER);
        if (index == -1) {
            username = Joiner.on(CommonConstant.DEFAULT_JOINER).join(Device.WEB.getType(), clientId, username);
            index = username.indexOf(CommonConstant.DEFAULT_JOINER);
        }
        AuthUserBO authUserBO = AuthContext.AuthService.loadByUsername(username.substring(index + 1),
                Device.getByType(Integer.parseInt(username.substring(0, index))));
        if (Objects.isNull(authUserBO)) {
            throw new UsernameNotFoundException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL.getMessage());
        }
        return AuthUserDetails.build(authUserBO);
    }
}
