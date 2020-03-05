package com.liyz.cloud.service.push.handler;

import com.liyz.cloud.service.push.model.vo.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.MessagePack;

/**
 * 注释: 消息返回体编码
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 14:01
 */
@Slf4j
@ChannelHandler.Sharable
public class MsgPackEncoderHandler extends MessageToByteEncoder<Response> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Response response, ByteBuf byteBuf) throws Exception {
        // 创建MessagePack对象
        MessagePack pack = new MessagePack();
        // 将对象编码为MessagePack格式的字节数组
        byteBuf.writeBytes(pack.write(response));
    }
}
