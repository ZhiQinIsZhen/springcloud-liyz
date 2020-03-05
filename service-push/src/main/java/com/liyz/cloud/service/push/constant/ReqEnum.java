package com.liyz.cloud.service.push.constant;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 11:19
 */
public final class ReqEnum {

    public static final String SPLITTER_ARGS = "_";

    // 操作类型
    public enum Op {
        unknown,
        heartbeat,
        login,
        logout,
        sub,
        call;
    }

    // 数据类型
    public enum DataType {
        balance,
        order;
    }

    // 数据交互类型
    public enum Action {
        insert,
        update;
    }

    // 协议
    public enum Protocol {
        http,
        websocket;
    }

}
