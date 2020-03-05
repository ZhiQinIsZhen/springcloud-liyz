package com.liyz.cloud.service.push.util;

import com.liyz.cloud.service.push.constant.ReqEnum;
import com.liyz.cloud.service.push.core.session.PushSession;
import com.liyz.cloud.service.push.core.storage.OnlineUserStorage;
import com.liyz.cloud.service.push.core.storage.SessionStorage;
import com.liyz.cloud.service.push.model.bo.user.LoginRequest;
import com.liyz.cloud.service.push.model.vo.DataMsg;
import com.liyz.cloud.service.push.model.vo.Request;
import com.liyz.cloud.service.push.model.vo.Response;
import com.liyz.cloud.service.push.service.UserService;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 注释: op消息处理工具类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 14:36
 */
@Slf4j
public final class OpMessageProcessUtil {

    public static void processLogin(Request request, PushSession pushSession, UserService userService) {
        LoginRequest loginRequest = new LoginRequest(request);
        DataMsg message = DataMsg.builder().type(ReqEnum.Op.login.name()).build();
        if (userService != null) {
            long userId = userService.getUserId(loginRequest);
            if (userId != -1L) {
                log.info("user login success, uid -> {}", userId);
                // 缓存用户信息
                OnlineUserStorage.add(userId, pushSession.getSessionId());
                // 重置session
                pushSession.setUserId(userId);
                SessionStorage.update(pushSession);
                // 返回信息
                pushSession.send(Response.ok("login success!", message));
                return;
            }
        }
        pushSession.send(Response.error("login failure!", message));
    }

    public static void processLogout(PushSession pushSession) {
        pushSession.getChannel().writeAndFlush(Response.ok("logout success!")).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }

    public static void processHeartbeat(PushSession pushSession) {
        DataMsg data = DataMsg.builder().type(ReqEnum.Op.heartbeat.name()).build();
        pushSession.send(Response.ok("service-push", data));
    }

    public static void processSubscribe(Request request, PushSession pushSession) {
        try {
            String subType = request.argsMap().get("type");
            ReqEnum.DataType dataType = ReqEnum.DataType.valueOf(subType);
            if (dataType == null) {
                SessionStorage.remove(pushSession.getSessionId());
                pushSession.getChannel().writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                return;
            }
            switch (dataType) {
                case order:

                    break;
                case balance:

                    break;
                default:

                    break;
            }
        } catch (Exception e) {
            log.error("sub exception ", e);
            pushSession.send(Response.error("system busy"));
        }
    }
}
