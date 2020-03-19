package com.liyz.cloud.service.member.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/19 10:43
 */
@Getter
@Setter
@Table(name = "resource")
public class ResourceDO implements Serializable {
    private static final long serialVersionUID = -5429676502708981666L;

    @Id
    private Integer id;

    private String url;

    private String method;

    private String describe;

    private String icon;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private Integer version;
}
