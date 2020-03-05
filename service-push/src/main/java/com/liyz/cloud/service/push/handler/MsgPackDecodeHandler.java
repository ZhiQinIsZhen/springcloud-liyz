package com.liyz.cloud.service.push.handler;

import com.liyz.cloud.service.push.model.vo.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * 注释: 消息体解码
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 13:58
 */
@Slf4j
@ChannelHandler.Sharable
public class MsgPackDecodeHandler extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        final int length = byteBuf.readableBytes();
        final byte[] array = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(), array, 0, length);

        MessagePack pack = new MessagePack();
        Request req = pack.read(array, Request.class);
        list.add(req);
    }
}
