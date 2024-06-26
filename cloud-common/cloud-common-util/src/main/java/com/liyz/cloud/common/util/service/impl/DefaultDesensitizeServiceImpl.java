package com.liyz.cloud.common.util.service.impl;

import com.liyz.cloud.common.util.annotation.Desensitization;
import com.liyz.cloud.common.util.constant.DesensitizationType;
import com.liyz.cloud.common.util.service.DesensitizeService;

/**
 * 注释:默认脱敏服务
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2022/4/20 19:50
 */
public class DefaultDesensitizeServiceImpl implements DesensitizeService {


    @Override
    public String desensitize(String value, Desensitization annotation) {
        return value;
    }

    @Override
    public DesensitizationType getType() {
        return DesensitizationType.DEFAULT;
    }
}
