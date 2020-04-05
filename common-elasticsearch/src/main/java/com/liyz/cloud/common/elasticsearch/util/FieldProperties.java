package com.liyz.cloud.common.elasticsearch.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 11:55
 */
@Getter
@Setter
@Builder
public class FieldProperties implements Serializable {
    private static final long serialVersionUID = 83303760057180749L;

    private String esField;

    private String classField;
}
