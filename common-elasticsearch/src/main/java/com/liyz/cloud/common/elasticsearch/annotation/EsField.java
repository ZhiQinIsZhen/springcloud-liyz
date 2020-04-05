package com.liyz.cloud.common.elasticsearch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 11:59
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EsField {

    /**
     * 字段对应关系
     * 1.只要加了该注解的字段才会同步到es中
     * 2.如果字段不一样，则name赋值，如果name没有赋值（默认值），那么默认取对应class字段名称
     *
     * @return
     */
    String name() default "";
    /**
     * 最后两个目前暂时不需要用，后续需要可使用
     */
    /**
     * 是否是key，及对应类的id
     *
     * @return
     */
    boolean key() default false;
    /**
     * 如果加了该注解又想不让其加入到es中，可以将该值设置为 false
     *
     * @return
     */
    boolean contain() default true;
}
