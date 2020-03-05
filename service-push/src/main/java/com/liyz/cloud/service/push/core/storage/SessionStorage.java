package com.liyz.cloud.service.push.core.storage;

import com.liyz.cloud.service.push.core.session.PushSession;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注释: session容器
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 11:27
 */
@Slf4j
public final class SessionStorage {

    private static final Map<String, PushSession> CHANNELS = new ConcurrentHashMap<>(1024);

    public static PushSession getAndSet(PushSession session) {
        return CHANNELS.computeIfAbsent(session.getSessionId(), (k) -> session);
    }

    public static void update(PushSession session) {
        CHANNELS.put(session.getSessionId(), session);
    }

    public static PushSession get(String clientId) {
        return CHANNELS.get(clientId);
    }

    public static Map<String, PushSession> getChannels() {
        return CHANNELS;
    }

    public static void remove(String clientId) {
        PushSession session = get(clientId);
        if (session != null) {
            // 删除的时候也删除在线用户信息
            Long userId = session.getUserId();
            if (userId != null) {
                OnlineUserStorage.remove(userId);
            }
            CHANNELS.remove(clientId);
        }
    }

    public static int count() {
        return CHANNELS.size();
    }

    /**
     * 出于性能的考虑，该方式不推荐使用
     *
     * @param session
     */
    @Deprecated
    public static void remove(PushSession session) {
        //查看是否包含
        if (CHANNELS.containsValue(session)) {
            String key = null;
            PushSession value = null;
            for (Map.Entry<String, PushSession> entry : CHANNELS.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                if (value == session) {
                    break;
                }
            }
            CHANNELS.remove(key);
        }
    }
}
