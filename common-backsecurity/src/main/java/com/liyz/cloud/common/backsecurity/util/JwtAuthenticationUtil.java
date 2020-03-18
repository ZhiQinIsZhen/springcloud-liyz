package com.liyz.cloud.common.backsecurity.util;

import com.alibaba.fastjson.JSON;
import com.liyz.cloud.common.backsecurity.core.JwtUserDetails;
import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 17:37
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtAuthenticationUtil {

    public static JwtUserDetails create(JwtUserBO user, Collection<? extends GrantedAuthority> authorities) {
        if (Objects.isNull(user)) {
            return null;
        }
        return new JwtUserDetails(
                user.getUserId(),
                user.getLoginName(),
                user.getLoginPwd(),
                user.getEmail(),
                user.getRole(),
                authorities,
                user.getWebTokenTime()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public static void authFail(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.error(CommonCodeEnum.AuthorizationFail)));
        httpServletResponse.addHeader("Session-Invalid","true");
    }
}
