package com.liyz.cloud.service.push.core.storage;

import com.liyz.cloud.service.push.core.listener.DataListener;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 注释: 监听事件容器
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 17:36
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListenerStorage<T extends DataListener> {

    private final Map<String, T> map = new ConcurrentHashMap<>(512);

    private static final ListenerStorage INSTANCE = new ListenerStorage();

    public static ListenerStorage getInstance() {
        return INSTANCE;
    }

    public void add(String dataType, Function<String, T> function) {
        map.computeIfAbsent(dataType, function);
    }

    public T get(String dataType) {
        return map.get(dataType);
    }

    public void remove(String dataType) {
        map.remove(dataType);
    }
}
