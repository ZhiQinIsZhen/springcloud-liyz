package com.liyz.cloud.common.model.bo.sharding;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/20 11:01
 */
@Setter
@Getter
public class UserBO implements Serializable {
    private static final long serialVersionUID = -5169131563209856390L;

    private Long id;

    private String city;

    private String name;

    private String pwd;
}
