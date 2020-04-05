package com.liyz.cloud.common.export.column;

import lombok.*;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 15:45
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnProperties implements Serializable {
    private static final long serialVersionUID = -2310380296631185551L;

    private String colName;

    private String colType;

    private String colField;
}
