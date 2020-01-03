package com.liyz.cloud.common.elasticsearch.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/7/16 15:17
 */
@Data
@Builder
@AllArgsConstructor
public class FieldProperties {
    private static final long serialVersionUID = 83303760057180749L;

    private String esField;

    private String classField;
}
