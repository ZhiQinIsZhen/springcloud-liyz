package com.liyz.cloud.common.model.exception.sms;

import com.liyz.cloud.common.base.enums.ServiceCodeEnum;
import com.liyz.cloud.common.base.exception.RemoteServiceException;
import lombok.extern.slf4j.Slf4j;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/11 16:59
 */
@Slf4j
public class RemoteSmsServiceException extends RemoteServiceException {
    private static final long serialVersionUID = -7614378655285011266L;

    public RemoteSmsServiceException() {
    }

    public RemoteSmsServiceException(String message) {
        super(message);
    }

    public RemoteSmsServiceException(String message, String code) {
        super(message, code);
    }

    public RemoteSmsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteSmsServiceException(ServiceCodeEnum serviceCodeEnum) {
        super(serviceCodeEnum);
        log.info("remoteSmsException {}  {}", serviceCodeEnum.getCode(), serviceCodeEnum.getMessage());
    }
}
