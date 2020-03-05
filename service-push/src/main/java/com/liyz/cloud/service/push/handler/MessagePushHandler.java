package com.liyz.cloud.service.push.handler;

import com.liyz.cloud.service.push.constant.ReqEnum;
import com.liyz.cloud.service.push.core.session.PushSession;
import com.liyz.cloud.service.push.core.storage.SessionStorage;
import com.liyz.cloud.service.push.model.vo.Request;
import com.liyz.cloud.service.push.service.DataService;
import com.liyz.cloud.service.push.service.UserService;
import com.liyz.cloud.service.push.util.OpMessageProcessUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 注释: 消息读取后根据类型处理
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 14:09
 */
@Slf4j
@ChannelHandler.Sharable
public class MessagePushHandler extends SimpleChannelInboundHandler<Request> {

    private final UserService userService;
    private final DataService dataService;

    public MessagePushHandler(UserService userService, DataService dataService) {
        this.userService = userService;
        this.dataService = dataService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        log.info("enter into channelRead0 ......");
        PushSession pushSession = SessionStorage.getAndSet(new PushSession(channelHandlerContext.channel(), null));

        ReqEnum.Op op = ReqEnum.Op.valueOf(request.getOp());
        if (op == null) {
            SessionStorage.remove(pushSession.getSessionId());
            channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            return;
        }
        switch (op) {
            case login:
                OpMessageProcessUtil.processLogin(request, pushSession, userService);
                break;
            case logout:
                OpMessageProcessUtil.processLogout(pushSession);
                break;
            case heartbeat:
                OpMessageProcessUtil.processHeartbeat(pushSession);
                break;
            case sub:

                break;
            case call:

                break;
            case unknown:

                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String clientId = ctx.channel().id().asLongText();
        SessionStorage.remove(clientId);
        ctx.close();
        log.error("channel error ", cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String clientId = ctx.channel().id().asLongText();
        SessionStorage.remove(clientId);
        log.info("channelInactive remove clientId :{}", clientId);
        super.channelInactive(ctx);
    }
}
