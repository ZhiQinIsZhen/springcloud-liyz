package com.liyz.cloud.common.redisson.selector;

import com.liyz.cloud.common.redisson.config.RedissonConfigurer;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/16 14:48
 */
public class RedissonImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[] {RedissonConfigurer.class.getName()};
    }
}
