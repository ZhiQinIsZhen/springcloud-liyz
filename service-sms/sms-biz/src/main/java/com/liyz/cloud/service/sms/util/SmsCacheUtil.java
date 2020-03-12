package com.liyz.cloud.service.sms.util;

import com.liyz.cloud.common.model.constant.common.CommonConstant;
import com.liyz.cloud.service.sms.model.MsgTemplateDO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/12 9:21
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SmsCacheUtil {

    private static Map<String, MsgTemplateDO> msgTemplateMap = new ConcurrentHashMap<>(64);

    public static MsgTemplateDO getMsgTemplate(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return msgTemplateMap.get(key);
    }

    public static MsgTemplateDO getMsgTemplate(MsgTemplateDO msgTemplateDO) {
        return getMsgTemplate(getKey(msgTemplateDO));
    }

    public static MsgTemplateDO pubMsgTemplate(String key, MsgTemplateDO msgTemplateDO) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return msgTemplateMap.putIfAbsent(key, msgTemplateDO);
    }

    public static MsgTemplateDO pubMsgTemplate(MsgTemplateDO msgTemplateDO) {
        return pubMsgTemplate(getKey(msgTemplateDO), msgTemplateDO);
    }

    private static String getKey(MsgTemplateDO msgTemplateDO) {
        if (Objects.isNull(msgTemplateDO) || Objects.isNull(msgTemplateDO.getCode())
                || Objects.isNull(msgTemplateDO.getType()) || StringUtils.isBlank(msgTemplateDO.getLocale())) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(msgTemplateDO.getCode())
                .append(CommonConstant.DEFAULT_SPLIT)
                .append(msgTemplateDO.getType())
                .append(CommonConstant.DEFAULT_SPLIT)
                .append(msgTemplateDO.getLocale());
        return sb.toString();
    }
}
