package com.liyz.cloud.api.web.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 19:16
 */
@ApiModel(value = "LoginDTO", description = "登陆请求数据")
@Data
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 8763053903614783354L;

    @ApiModelProperty(value = "登录名", example = "159887859", required = true)
    @NotBlank(message = "登录名不能为空", groups = {Login.class})
    private String loginName;

    @ApiModelProperty(value = "密码，8-20位数字或字母组成", example = "Aa123456", required = true)
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$", groups = {Login.class}, message = "请输入8到20位数字和字母组合")
    private String loginPwd;

    @ApiModelProperty(value = "登录方式 0：手机，1：邮箱", example = "0", required = true)
    @NotNull(message = "登陆方式不能为空", groups = {Login.class})
    private Integer type;

    public interface Login {}
}
