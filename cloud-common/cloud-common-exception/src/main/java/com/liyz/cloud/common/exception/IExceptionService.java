package com.liyz.cloud.common.exception;

/**
 * Desc:exception interface
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 10:35
 */
public interface IExceptionService {

    /**
     * 获取异常code
     *
     * @return code
     */
    String getCode();

    /**
     * 获取异常信息
     *
     * @return 异常信息
     */
    String getMessage();
}
