package com.liyz.cloud.common.base.remote.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/18 16:33
 */
@Getter
@Setter
public class ResourcesBO implements Serializable {
    private static final long serialVersionUID = -806277318234391106L;

    private Long id;

    private String describe;

    private String url;

    private String method;

    /**
     * 父节点
     */
    private Integer parentId;

    private String icon;

    private String route;

    /**
     * 子节点
     */
    private List<ResourcesBO> children = new ArrayList<>();
}
