package com.liyz.cloud.service.push.model.vo;

import com.google.common.base.Splitter;
import lombok.Data;
import org.msgpack.annotation.Index;
import org.msgpack.annotation.Message;

import java.io.Serializable;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 12:20
 */
@Data
@Message
public class Request implements Serializable {
    private static final long serialVersionUID = 6384053827304807697L;

    @Index(0)
    private String biz;

    @Index(1)
    private String op;

    @Index(2)
    private String args;

    public Map<String, String> argsMap() {
        return Splitter.on("&").withKeyValueSeparator("=").split(args);
    }
}
