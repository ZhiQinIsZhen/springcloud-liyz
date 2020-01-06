package com.liyz.cloud.common.model.bo.member;

import com.liyz.cloud.common.model.constant.member.MemberEnum;
import lombok.Data;

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
@Data
public class LoginUserInfoBO implements Serializable {
    private static final long serialVersionUID = -727638452434699928L;

    @NotNull(groups = {KickDown.class}, message = "用户ID不能为空")
    private Long userId;

    @NotNull(groups = {KickDown.class}, message = "设备类型不能为空")
    private MemberEnum.DeviceEnum deviceEnum;

    @NotBlank(groups = {Auth.class}, message = "登陆名不能为空")
    private String loginName;

    public interface Auth {}

    public interface KickDown {}
}
