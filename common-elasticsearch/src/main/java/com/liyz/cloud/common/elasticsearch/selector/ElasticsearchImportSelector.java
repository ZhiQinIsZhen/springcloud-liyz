package com.liyz.cloud.common.elasticsearch.selector;

import com.liyz.cloud.common.elasticsearch.config.ElasticSearchConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/16 14:48
 */
public class ElasticsearchImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[] {ElasticSearchConfig.class.getName()};
    }
}
