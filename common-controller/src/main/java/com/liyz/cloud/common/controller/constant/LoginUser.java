package com.liyz.cloud.common.controller.constant;

import lombok.Data;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/12/16 22:24
 */
@Data
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1147807763673070977L;

    private String userName;
}
