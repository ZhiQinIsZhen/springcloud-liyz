package com.liyz.cloud.service.auth.util;

import com.google.common.base.Joiner;
import com.liyz.cloud.common.util.constant.CommonConstant;
import lombok.experimental.UtilityClass;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/30 16:11
 */
@UtilityClass
public class RedisUtil {

    /**
     * redis前缀
     */
    public final String REDIS_PREFIX = "auth";

    public final String LOGIN_KEY = "loginKey";

    /**
     * 获取redis的key
     *
     * @param keys
     * @return
     */
    public static String getRedisKey(String... keys) {
        return Joiner.on(CommonConstant.DEFAULT_JOINER).join(REDIS_PREFIX, LOGIN_KEY, keys);
    }
}
