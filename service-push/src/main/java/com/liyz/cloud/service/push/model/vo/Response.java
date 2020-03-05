package com.liyz.cloud.service.push.model.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 13:39
 */
@Data
@Builder
@AllArgsConstructor
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -4896382891229070543L;

    private static final int OK = 0;
    private static final int FAILURE = 1;

    private int code;

    private DataMsg data;

    private String message;

    public static Response error(String message) {
        return error(message, null);
    }

    public static Response error(String message, DataMsg data) {
        return Response.builder().code(FAILURE).message(message).data(data).build();
    }

    public static Response ok(DataMsg data) {
        return ok(null, data);
    }

    public static Response ok(String message) {
        return ok(message, null);
    }

    public static Response ok(String message, DataMsg data) {
        return Response.builder().code(OK).message(message).data(data).build();
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
