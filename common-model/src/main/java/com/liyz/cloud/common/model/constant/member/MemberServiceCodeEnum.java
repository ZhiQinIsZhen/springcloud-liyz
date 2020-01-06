package com.liyz.cloud.common.model.constant.member;

import com.liyz.cloud.common.base.enums.ServiceCodeEnum;
import lombok.AllArgsConstructor;

/**
 * 注释: member错误码  以20***开头
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 15:01
 */
@AllArgsConstructor
public enum MemberServiceCodeEnum implements ServiceCodeEnum {

    MobileExsist("20001", "手机号已存在"),
    EmailExsist("20002", "邮箱号已存在")
    ;

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
