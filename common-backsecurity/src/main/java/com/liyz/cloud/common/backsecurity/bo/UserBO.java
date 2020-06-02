package com.liyz.cloud.common.backsecurity.bo;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/1 17:15
 */
public class UserBO extends User {

    /**
     * 用户ID
     */
    @Getter
    private Long id;

    /**
     * 部门ID
     */
    @Getter
    private Integer deptId;

    public UserBO(Long id, Integer deptId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.deptId = deptId;
    }
}
