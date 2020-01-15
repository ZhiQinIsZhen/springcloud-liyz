package com.liyz.cloud.common.elasticsearch.annotation;

import com.liyz.cloud.common.elasticsearch.selector.ElasticsearchImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/16 14:41
 */
//@Target({ElementType.TYPE})
//@Retention(RetentionPolicy.RUNTIME)
//@Import({ElasticsearchImportSelector.class})
public @interface EnableElasticSearch {
}
