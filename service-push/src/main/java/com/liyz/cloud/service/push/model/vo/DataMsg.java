package com.liyz.cloud.service.push.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 注释: 消息返回体
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 13:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataMsg implements Serializable {
    private static final long serialVersionUID = -7238011997597461331L;

    private String biz;

    private String action;

    private String type;

    private String subType;

    private List data;
}
