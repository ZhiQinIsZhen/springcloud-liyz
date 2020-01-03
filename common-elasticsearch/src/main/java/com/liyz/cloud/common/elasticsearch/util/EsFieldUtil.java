package com.liyz.cloud.common.elasticsearch.util;

import com.qjdchina.ai.common.base.annotation.EsField;
import com.qjdchina.ai.common.base.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 注释:处理es与sql中field的工具类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/7/16 14:30
 */
@Slf4j
public class EsFieldUtil<T> {

    private static ThreadLocal<List<FieldProperties>> fieldList = new ThreadLocal<>();

    /**
     * 初始化fieldList
     *
     * @param clazz
     */
    private static <T> void initFieldList(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            throw new IllegalArgumentException("class no fields");
        }
        List<FieldProperties> list = new ArrayList<>();
        for (int i = 0, length = fields.length; i < length; i++) {
            Field field = fields[i];
            if (!field.isAnnotationPresent(EsField.class)) {
                continue;
            }
            EsField esField = field.getAnnotation(EsField.class);
            if (StringUtils.isBlank(esField.name())) {
                list.add(new FieldProperties(field.getName(), field.getName()));
            } else {
                list.add(new FieldProperties(esField.name(), field.getName()));
            }
        }
        if (list.size() == 0) {
            throw new IllegalArgumentException("class no EsField Annotation");
        }
        fieldList.set(list);
    }

    /**
     * 获取fieldMap
     *
     * @param item
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> getFieldMap(T item) {
        List<FieldProperties> list = fieldList.get();
        if (list == null || list.size() == 0) {
            initFieldList(item.getClass());
            list = fieldList.get();
        }
        Map<String, Object> map = new HashMap<>();
        String methodName;
        for (FieldProperties filed : list) {
            try {
                methodName = "get" + filed.getClassField().substring(0, 1).toUpperCase() + filed.getClassField().substring(1);
                Object columnObj = item.getClass().getMethod(methodName).invoke(item);
                if (columnObj == null) {
                    map.put(filed.getEsField(), columnObj);
                    continue;
                }
                if (columnObj instanceof Date) {
                    map.put(filed.getEsField(), DateUtil.formatdate((Date) columnObj));
                } else if (columnObj instanceof String) {
                    map.put(filed.getEsField(), columnObj.toString()
                            .replaceAll("\\n", "。")
                            .replaceAll("\\r", "。"));
                } else {
                    map.put(filed.getEsField(), columnObj);
                }
            } catch (Exception e) {
                log.error("EsFieldUtil error :{}", e);
            }
        }
        return map;
    }
}
