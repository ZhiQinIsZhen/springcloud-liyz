package com.liyz.cloud.service.push.handler;

import com.liyz.cloud.service.push.model.vo.Response;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * 注释: response handler
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 14:04
 */
@Slf4j
@ChannelHandler.Sharable
public class TextResponseHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof Response) {
            Response response = (Response) msg;
            log.info("msg is response :{}", response);
            final TextWebSocketFrame frame = new TextWebSocketFrame();
            frame.content().writeBytes(response.toString().getBytes());
            ctx.writeAndFlush(frame);
        }
        super.write(ctx, msg, promise);
    }
}
