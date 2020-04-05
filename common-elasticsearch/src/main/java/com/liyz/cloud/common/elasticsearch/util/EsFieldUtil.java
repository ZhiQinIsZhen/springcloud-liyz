package com.liyz.cloud.common.elasticsearch.util;

import com.liyz.cloud.common.elasticsearch.annotation.EsField;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 11:54
 */
public class EsFieldUtil<T> {

    private static final Log logger = LogFactory.getLog(EsFieldUtil.class);
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
            if (esField.name() == null || esField.name().trim().length() == 0) {
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
                    map.put(filed.getEsField(), formatDate((Date) columnObj));
                } else if (columnObj instanceof String) {
                    map.put(filed.getEsField(), columnObj.toString()
                            .replaceAll("\\n", "。")
                            .replaceAll("\\r", "。"));
                } else {
                    map.put(filed.getEsField(), columnObj);
                }
            } catch (Exception e) {
                logger.error("EsFieldUtil error :", e);
            }
        }
        return map;
    }

    private static String formatDate(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
