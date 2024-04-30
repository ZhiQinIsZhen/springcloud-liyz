package com.liyz.cloud.common.util.service.impl;

import com.liyz.cloud.common.util.CryptoUtil;
import com.liyz.cloud.common.util.annotation.Desensitization;
import com.liyz.cloud.common.util.constant.DesensitizationType;
import com.liyz.cloud.common.util.service.DesensitizeService;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 注释:加解密脱敏
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2022/4/21 13:29
 */
public class EncryptDecryptDesensitizeServiceImpl implements DesensitizeService {

    @Override
    public String desensitize(String value, Desensitization annotation) {
        if (StringUtils.isNotBlank(value)) {
            if (Objects.isNull(annotation)) {
                value = CryptoUtil.Symmetric.encryptAES(value, "rZxl3zy!rZxl3zy!");
            } else {
                value = CryptoUtil.Symmetric.decryptAES(value, "rZxl3zy!rZxl3zy!");
            }
        }
        return value;
    }

    @Override
    public DesensitizationType getType() {
        return DesensitizationType.ENCRYPT_DECRYPT;
    }
}
