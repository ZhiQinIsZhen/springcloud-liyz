package com.liyz.cloud.api.web.vo.es;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/2/29 15:53
 */
@Data
public class NearByVO implements Serializable {
    private static final long serialVersionUID = -6969232713894878668L;

    private String id;

    private String wxNo;

    private String nickName;

    private String sex;

    private Date creteTime;
}
