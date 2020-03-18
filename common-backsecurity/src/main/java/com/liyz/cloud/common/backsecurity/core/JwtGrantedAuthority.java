package com.liyz.cloud.common.backsecurity.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/18 15:57
 */
@Getter
@Setter
@AllArgsConstructor
public class JwtGrantedAuthority implements GrantedAuthority {

    private String url;

    private String method;

    @Override
    public String getAuthority() {
        StringBuilder sb = new StringBuilder();
        sb.append(method).append(";").append(url);
        return sb.toString();
    }
}
