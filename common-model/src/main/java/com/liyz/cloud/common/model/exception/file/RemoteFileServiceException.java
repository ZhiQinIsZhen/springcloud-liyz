package com.liyz.cloud.common.model.exception.file;

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
public class RemoteFileServiceException extends RemoteServiceException {

    private static final long serialVersionUID = 6574646237803730595L;

    public RemoteFileServiceException() {
    }

    public RemoteFileServiceException(String message) {
        super(message);
    }

    public RemoteFileServiceException(String message, String code) {
        super(message, code);
    }

    public RemoteFileServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteFileServiceException(ServiceCodeEnum serviceCodeEnum) {
        super(serviceCodeEnum);
        log.info("remoteFileException {}  {}", serviceCodeEnum.getCode(), serviceCodeEnum.getMessage());
    }
}
