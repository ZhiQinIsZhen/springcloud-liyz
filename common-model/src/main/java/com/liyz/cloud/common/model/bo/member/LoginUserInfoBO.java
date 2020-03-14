package com.liyz.cloud.common.model.bo.member;

import com.liyz.cloud.common.model.constant.member.MemberEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/6 13:32
 */
@Getter
@Setter
public class LoginUserInfoBO implements Serializable {
    private static final long serialVersionUID = -727638452434699928L;

    @NotNull(groups = {KickDown.class, Login.class}, message = "用户ID不能为空")
    private Long userId;

    @NotNull(groups = {KickDown.class, Login.class}, message = "设备类型不能为空")
    private MemberEnum.DeviceEnum deviceEnum;

    @NotBlank(groups = {Auth.class}, message = "登陆名不能为空")
    private String loginName;

    @NotBlank(groups = {Login.class}, message = "IP不能为空")
    private String ip;

    public interface Auth {}

    public interface KickDown {}

    public interface Login {}
}
