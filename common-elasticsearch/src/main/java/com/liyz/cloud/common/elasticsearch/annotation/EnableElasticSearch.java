package com.liyz.cloud.common.elasticsearch.annotation;

import com.liyz.cloud.common.elasticsearch.selector.ElasticsearchImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 注释:es注解
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 11:15
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ElasticsearchImportSelector.class})
public @interface EnableElasticSearch {
}
