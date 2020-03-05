package com.liyz.cloud.service.push.model.bo.sub;

import lombok.Data;

import java.io.Serializable;

/**
 * 注释: http token 获取userInfo
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 23:12
 */
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 5362016121584425026L;

    private String code;

    private String message;

    private Long data;
}
