package com.liyz.cloud.common.elasticsearch.selector;

import com.liyz.cloud.common.elasticsearch.config.ElasticSearchConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 11:37
 */
public class ElasticsearchImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {ElasticSearchConfig.class.getName()};
    }
}
