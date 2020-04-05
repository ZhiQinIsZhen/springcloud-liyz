package com.liyz.cloud.common.export.exception;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 15:47
 */
public class ExportException extends RuntimeException {

    public ExportException(String msg) {
        super(msg);
    }

    public ExportException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
