package com.liyz.cloud.service.sso.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/4 0:09
 */
public class MyUser extends User {

    private final Long userId;

    private final String email;

    private final Date lastWebPasswordResetDate;

    private final Date lastAppPasswordResetDate;

    public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId, String email, Date lastWebPasswordResetDate, Date lastAppPasswordResetDate) {
        super(username, password, authorities);
        this.userId = userId;
        this.email = email;
        this.lastWebPasswordResetDate = lastWebPasswordResetDate;
        this.lastAppPasswordResetDate = lastAppPasswordResetDate;
    }

    @JsonIgnore
    public Long getUserId() {
        return userId;
    }

    @JsonIgnore
    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public Date getLastWebPasswordResetDate() {
        return lastWebPasswordResetDate;
    }

    @JsonIgnore
    public Date getLastAppPasswordResetDate() {
        return lastAppPasswordResetDate;
    }
}
