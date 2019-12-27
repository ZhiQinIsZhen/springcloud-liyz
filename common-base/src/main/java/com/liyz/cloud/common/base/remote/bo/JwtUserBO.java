package com.liyz.cloud.common.base.remote.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/11/20 9:57
 */
@Data
public class JwtUserBO implements Serializable {
    private static final long serialVersionUID = 1560387064652487267L;

    private Long userId;

    private String loginName;

    private String nickName;

    private String userName;

    private String mobile;

    private String email;

    private String loginPwd;

    private Date regTime;

    private Date webTokenTime;

    private Date appTokenTime;
}
