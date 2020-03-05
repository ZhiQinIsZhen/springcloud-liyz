package com.liyz.cloud.service.push.model.bo.user;

import com.liyz.cloud.service.push.model.vo.Request;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 注释: 登录请求
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 14:39
 */
@Data
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = -3514902137092560689L;

    private String token;

    public LoginRequest(Request request) {
        this(request.argsMap());
    }

    public LoginRequest(Map<String, String> map) {
        token = map.get("token");
    }
}
