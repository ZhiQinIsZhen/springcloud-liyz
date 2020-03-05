package com.liyz.cloud.service.push.core.storage;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 注释: 数据类型容器
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 16:20
 */
@Slf4j
public final class DataTypeChannelStorage {

    private final static Map<String, Map<String, Boolean>> DATA_TYPE_MAP_CACHE = new ConcurrentHashMap<>(64);

    public static Map<String, Boolean> add(String dataType, Function<String, Map<String, Boolean>> function) {
        return DATA_TYPE_MAP_CACHE.computeIfAbsent(dataType, function);
    }

    public static Map<String, Boolean> get(String dataType) {
        return DATA_TYPE_MAP_CACHE.get(dataType);
    }

    public static void remove(String dataType) {
        DATA_TYPE_MAP_CACHE.remove(dataType);
    }

    public static Set<String> dataTypes() {
        return DATA_TYPE_MAP_CACHE.keySet();
    }
}
