package com.liyz.cloud.service.push.core.listener;

import com.liyz.cloud.service.push.core.session.PushSession;
import com.liyz.cloud.service.push.core.storage.DataTypeChannelStorage;
import com.liyz.cloud.service.push.core.storage.SessionStorage;
import com.liyz.cloud.service.push.model.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 16:18
 */
@Slf4j
public abstract class DataListener<T> implements IListener<T> {

    public abstract Response<T> wrapper(String dataType, T data);

    @Override
    public void update(String dataType, T data) {
        Map<String, Boolean> typeMap = DataTypeChannelStorage.get(dataType);

        Response<T> response = wrapper(dataType, data);
        if (CollectionUtils.isEmpty(typeMap)) {
            return;
        }
        typeMap.keySet().forEach(client -> {
            PushSession pushSession = SessionStorage.get(client);
            if (pushSession != null) {
                pushSession.send(response);
            } else {
                log.info("client is not exsist, client:{}", client);
                typeMap.remove(client);
            }
        });
    }

    @Override
    public void addSubscriber(String dataType, String clientId) {
        removeLatestSubscriber(dataType, clientId);
        if (needLogin()) {
            PushSession pushSession = SessionStorage.get(clientId);
            if (Objects.isNull(pushSession) || Objects.isNull(pushSession.getUserId())) {
                return;
            }
        }

        DataTypeChannelStorage.add(dataType, (k) -> new ConcurrentHashMap<>(1024)).put(clientId, Boolean.TRUE);
    }

    @Override
    public boolean removeSubscriber(String dataType, String clientId) {
        return getSubscribesByDataType(dataType).remove(clientId);
    }

    @Override
    public Set<String> getSubscribesByDataType(String datatype) {
        return DataTypeChannelStorage.get(datatype).keySet();
    }

    private void removeLatestSubscriber(String dataType, String clientId) {
        Set<String> dataTypes = DataTypeChannelStorage.dataTypes();
        dataTypes.forEach(type -> {
            if (getType(dataType).equals(getType(type))) {
                log.info("remove : {}, dataType : {}", clientId, dataType);
                DataTypeChannelStorage.get(type).remove(clientId);
            }
        });
    }
}
