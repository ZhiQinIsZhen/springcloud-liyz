package com.liyz.cloud.service.member.exception;

import com.liyz.cloud.common.base.enums.ServiceCodeEnum;
import com.liyz.cloud.common.base.exception.RemoteServiceException;
import lombok.extern.slf4j.Slf4j;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/29 14:39
 */
@Slf4j
public class RemoteMemberServiceException extends RemoteServiceException {

    private static final long serialVersionUID = 6574646237803730595L;

    public RemoteMemberServiceException() {
    }

    public RemoteMemberServiceException(String message) {
        super(message);
    }

    public RemoteMemberServiceException(String message, String code) {
        super(message, code);
    }

    public RemoteMemberServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteMemberServiceException(ServiceCodeEnum serviceCodeEnum) {
        super(serviceCodeEnum);
        log.info("remoteMemberException {}  {}", serviceCodeEnum.getCode(), serviceCodeEnum.getMessage());
    }
}
