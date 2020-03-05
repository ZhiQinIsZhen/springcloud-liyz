package com.liyz.cloud.service.push.handler;

import com.liyz.cloud.service.push.service.DataService;
import com.liyz.cloud.service.push.service.UserService;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 注释: netty channel 初始化类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 10:44
 */
@Slf4j
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final String WEB_SOCKET_PATH = "/websocket";

    private final UserService userService;

    private final DataService dataService;

    public WebSocketChannelInitializer(UserService userService, DataService dataService) {
        this.userService = userService;
        this.dataService = dataService;
    }

    @Override
    protected void initChannel(final SocketChannel socketChannel) throws Exception {
        log.info("initChannel .... {}", socketChannel);
        final ChannelPipeline pipeline = socketChannel.pipeline();
        // HttpServerCodec ,将请求和应答消息编码或者解码为HTTP消息
        pipeline.addLast("codec-http", new HttpServerCodec());
        // 将HTTP消息的多个部分组合成一条完整的HTTP消息;
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        // WebSocket升级
        pipeline.addLast("web-socket", new WebSocketServerProtocolHandler(WEB_SOCKET_PATH, null, true));
        // 空闲检查, 60秒就关闭连接
        pipeline.addLast("idle", new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS));
        // 心跳处理
        pipeline.addLast("heart", new HeartbeatHandler());
        // 编码转换器
        pipeline.addLast("decoder", new MsgPackDecodeHandler());
        pipeline.addLast("encoder", new MsgPackEncoderHandler());
        pipeline.addLast("response", new TextResponseHandler());
        // 业务逻辑处理
        pipeline.addLast("msg-push", new MessagePushHandler(userService, dataService));
    }
}
