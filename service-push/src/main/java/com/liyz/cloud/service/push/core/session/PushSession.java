package com.liyz.cloud.service.push.core.session;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 11:22
 */
@Data
@AllArgsConstructor
public class PushSession implements ISession {

    private final Channel channel;

    private Long userId;

    @Override
    public String getSessionId() {
        return channel.id().asLongText();
    }

    @Override
    public boolean isWriteAble() {
        return channel.isWritable();
    }

    @Override
    public void send(Object data) {
        if (isWriteAble()) {
            channel.writeAndFlush(data);
        }
    }
}
