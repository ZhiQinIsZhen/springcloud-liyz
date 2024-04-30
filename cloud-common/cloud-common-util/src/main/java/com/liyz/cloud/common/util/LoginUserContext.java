package com.liyz.cloud.common.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/30 9:21
 */
@UtilityClass
public class LoginUserContext {

    public static final String ATTACHMENT_LOGIN_USER = "loginUser";
    private static final Long DEFAULT_SYSTEM_USER_ID = -1L;

    private static final InheritableThreadLocal<Long> innerContext = new InheritableThreadLocal<>();

    public static Long getLoginId() {
        String LoginId = null;
        if (StringUtils.isBlank(LoginId) || !isLong(LoginId)) {
            return DEFAULT_SYSTEM_USER_ID;
        }
        return Long.valueOf(LoginId);
    }

    private static boolean isLong(String s) {
        if (StringUtils.isBlank(s)) {
            return false;
        } else {
            try {
                Long.parseLong(s);
                return true;
            } catch (NumberFormatException var2) {
                return false;
            }
        }
    }
}
