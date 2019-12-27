package com.liyz.cloud.common.base.filter;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.liyz.cloud.common.base.annotation.Desensitization;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 注释:自定义json脱敏过滤器
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/15 9:27
 */
@Slf4j
public class DesensitizationContextValueFilter implements ContextValueFilter {

    @Override
    public Object process(BeanContext beanContext, Object o, String s, Object o1) {
        if (beanContext == null || o1 == null || !(o1 instanceof String)) {
            return o1;
        }
        Desensitization annotation = beanContext.getAnnation(Desensitization.class);
        if (annotation == null) {
            return o1;
        }
        String propertyValue = (String) o1;
        if (StringUtils.isEmpty(propertyValue)) {
            return "";
        }
        int length = propertyValue.trim().length();
        switch (annotation.value()) {
            case SELF_DEFINITION:
                int beginIndex = annotation.beginIndex();
                int endIndex = annotation.endIndex();
                if (beginIndex >= 0 && beginIndex < endIndex && endIndex <= length) {
                    StringBuilder format = new StringBuilder(beginIndex > 0 ? "%s" : "");
                    for (int i = endIndex - beginIndex; i > 0; i--) {
                        format.append("*");
                    }
                    format.append(endIndex == length ? "" : "%s");
                    if (beginIndex > 0) {
                        propertyValue = String.format(format.toString(), propertyValue.substring(0, beginIndex), propertyValue.substring(endIndex));
                    } else {
                        propertyValue = String.format(format.toString(), propertyValue.substring(endIndex));
                    }
                }
                break;
            case MOBILE:
                if (length == 11) {
                    propertyValue = propertyValue.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                } else {
                    propertyValue = String.format("%s****%s",propertyValue.substring(0, 3), propertyValue.substring(7));
                }
                break;
            case EMAIL:
                propertyValue = propertyValue.replaceAll("(\\w+)\\w{5}@(\\w+)", "$1****$2");
                break;
            case REAL_NAME:
                if (length > 1) {
                    propertyValue = propertyValue.replaceAll("(\\W)(\\W+)", "$1*");
                }
                break;
            case CARD_NO:
                if (length == 18) {
                    propertyValue = propertyValue.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1****$2");
                } else {
                    propertyValue = String.format("%s****%s",propertyValue.substring(0, 4), propertyValue.substring(14));
                }
                break;
            default:
                break;
        }
        return propertyValue;
    }
}
