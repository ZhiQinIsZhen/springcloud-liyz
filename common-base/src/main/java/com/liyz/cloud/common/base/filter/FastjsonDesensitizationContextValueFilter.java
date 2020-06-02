package com.liyz.cloud.common.base.filter;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.liyz.cloud.common.base.annotation.Desensitization;
import com.liyz.cloud.common.base.service.DesensitizeService;
import org.apache.commons.lang3.StringUtils;

/**
 * 注释:自定义fastjson脱敏过滤器
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/15 9:27
 */
public class FastjsonDesensitizationContextValueFilter implements ContextValueFilter, DesensitizeService {

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
        return desensitize(propertyValue, annotation);
    }
}
