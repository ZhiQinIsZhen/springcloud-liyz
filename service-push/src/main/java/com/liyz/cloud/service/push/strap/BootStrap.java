package com.liyz.cloud.service.push.strap;

import com.liyz.cloud.service.push.handler.WebSocketChannelInitializer;
import com.liyz.cloud.service.push.service.DataService;
import com.liyz.cloud.service.push.service.UserService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 注释: netty 启动线程
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 10:14
 */
@Slf4j
@Component
public class BootStrap implements Runnable {

    @Autowired
    UserService userService;
    @Autowired
    DataService dataService;

    @Value("${netty.use.epoll}")
    private boolean useEpoll;
    @Value("${netty.port}")
    private int port;

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workGroup = null;

    @Override
    public void run() {
        log.info("push server start ...");
        try {
            Class channelClass;
            if (useEpoll) {
                bossGroup = new EpollEventLoopGroup(1, new DefaultThreadFactory("boss", true));
                workGroup = new EpollEventLoopGroup(10, new DefaultThreadFactory("worker", true));
                channelClass = EpollServerSocketChannel.class;
            } else {
                bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("boss", true));
                workGroup = new NioEventLoopGroup(10, new DefaultThreadFactory("worker", true));
                channelClass = NioServerSocketChannel.class;
            }
            final ServerBootstrap bootstrap = new ServerBootstrap();
            // 地址复用
            bootstrap.option(ChannelOption.SO_REUSEADDR, true);
            // 发送缓冲区
            bootstrap.option(ChannelOption.SO_SNDBUF, 64 * 1024);
            // 接收缓冲区
            bootstrap.option(ChannelOption.SO_RCVBUF, 64 * 1024);
            // 客户端调用超时时间
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15000);
            // 低延时多交互次数
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            // 探测客户端存活性
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            // 客户端连接超时间
            bootstrap.option(ChannelOption.SO_TIMEOUT, 15000);
            // 处理线程全满时，临时缓存的请求个数
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);

            final WriteBufferWaterMark write = new WriteBufferWaterMark(512 * 1024, 1024 * 1024);
            bootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK, write);
            bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            // 动态调整
            bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT);
            bootstrap.group(bossGroup, workGroup).channel(channelClass).childHandler(
                    new WebSocketChannelInitializer(userService, dataService)
            );

            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            log.info("push server start success");
            channelFuture.channel().closeFuture().sync();
        } catch (final InterruptedException e) {
            log.error("netty start fail, exception ", e);
        } finally {
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }
            if (workGroup != null) {
                workGroup.shutdownGracefully();
            }
        }
    }

//    @PreDestroy
    public void stop() {
        log.info("push server stop ...");
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workGroup != null) {
            workGroup.shutdownGracefully();
        }
        log.info("push server stop success");
    }
}
