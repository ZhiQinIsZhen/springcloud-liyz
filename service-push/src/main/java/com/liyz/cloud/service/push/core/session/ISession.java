package com.liyz.cloud.service.push.core.session;

import io.netty.channel.Channel;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 11:15
 */
public interface ISession {

    String getSessionId();

    Long getUserId();

    boolean isWriteAble();

    void send(Object data);

    Channel getChannel();
}
