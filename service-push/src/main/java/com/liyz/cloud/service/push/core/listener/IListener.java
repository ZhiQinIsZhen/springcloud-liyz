package com.liyz.cloud.service.push.core.listener;


import com.liyz.cloud.service.push.constant.ReqEnum;

import java.util.Set;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 11:17
 */
public interface IListener<T> {

    void update(String dataType, T data);

    void addSubscriber(String dataType, String clientId);

    boolean removeSubscriber(String dataType, String clientId);

    Set<String> getSubscribesByDataType(String datatype);

    default String getType(String dataType) {
        String[] dataTypeStrs = dataType.split(ReqEnum.SPLITTER_ARGS);
        if (dataTypeStrs.length >= 2) {
            return dataTypeStrs[1];
        } else {
            return null;
        }
    }

    default boolean needLogin() {
        return false;
    }
}
