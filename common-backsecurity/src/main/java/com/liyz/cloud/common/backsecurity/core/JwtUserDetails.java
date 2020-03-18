package com.liyz.cloud.common.backsecurity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 17:28
 */
public class JwtUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String email;
    private final String role;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Date lastPasswordResetDate;

    public JwtUserDetails(Long id, String username, String password, String email, String role,
                   Collection<? extends GrantedAuthority> authorities, Date lastPasswordResetDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
        this.authorities = authorities;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    @JsonIgnore
    public String getRole() {
        return role;
    }

}
