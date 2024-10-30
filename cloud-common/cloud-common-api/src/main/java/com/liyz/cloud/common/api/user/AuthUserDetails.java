package com.liyz.cloud.common.api.user;

import com.liyz.cloud.common.base.util.BeanUtil;
import com.liyz.cloud.common.feign.bo.auth.AuthUserBO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.util.Collection;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/14 15:39
 */
@Getter
@Setter
public class AuthUserDetails extends User {
    @Serial
    private static final long serialVersionUID = 1L;

    private final AuthUserBO authUser;

    public AuthUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                           AuthUserBO authUser) {
        super(username, password, authorities);
        this.authUser = authUser;
    }

    public static AuthUserDetails build(AuthUserBO authUserBO) {
        return new AuthUserDetails(authUserBO.getUsername(), authUserBO.getPassword(),
                BeanUtil.copyList(authUserBO.getAuthorities(), AuthGrantedAuthority::new), authUserBO);
    }
}
