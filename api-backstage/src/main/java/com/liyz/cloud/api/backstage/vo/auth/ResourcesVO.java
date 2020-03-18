package com.liyz.cloud.api.backstage.vo.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "ResourcesVO", description = "资源权限信息")
public class ResourcesVO implements Serializable {
    private static final long serialVersionUID = -806277318234391106L;

    @ApiModelProperty(value = "id", example = "10001")
    private Long id;

    @ApiModelProperty(value = "资源名称", example = "用户信息")
    private String describe;

    @ApiModelProperty(value = "资源url", example = "/user/getInfo")
    private String url;

    @ApiModelProperty(value = "资源请求方法", example = "post")
    private String method;

    /**
     * 父节点
     */
    @ApiModelProperty(value = "父节点资源id", example = "1")
    private Integer parentId;

    @ApiModelProperty(value = "资源节点显示图片", example = "32345")
    private String icon;

    @ApiModelProperty(value = "资源路线", example = "10001")
    private String route;

    /**
     * 子节点
     */
    @ApiModelProperty(value = "其子节点", example = "10001")
    private List<ResourcesVO> children = new ArrayList<>();
}
