package com.liyz.cloud.service.push.core.storage;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注释: 在线用户容器
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 11:31
 */
@Slf4j
public class OnlineUserStorage {

    private static final Map<Long, String> CHANNELS = new ConcurrentHashMap<>(1024);

    public static void add(long userId, String clientId) {
        CHANNELS.put(userId, clientId);
    }

    public static String get(long userId) {
        return CHANNELS.get(userId);
    }

    public static void remove(long userId) {
        CHANNELS.remove(userId);
    }

    public static int count(){
        return CHANNELS.size();
    }

    /**
     * 出于性能的考虑，该方式不推荐使用
     *
     * @param clientId
     */
    @Deprecated
    public static void remove(String clientId) {
        //查看是否包含
        if (CHANNELS.containsValue(clientId)) {
            Long key = null;
            String value = null;
            for (Map.Entry<Long, String> entry : CHANNELS.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                if (value.equals(clientId)) {
                    break;
                }
            }
            CHANNELS.remove(key);
        }
    }

    /**
     * 出于性能的考虑，该方式不推荐使用
     *
     * @param clientId
     * @return
     */
    @Deprecated
    public static boolean hasClient(String clientId) {
        return CHANNELS.containsValue(clientId);
    }
}
