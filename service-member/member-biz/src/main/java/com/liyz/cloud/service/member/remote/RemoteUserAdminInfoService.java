package com.liyz.cloud.service.member.remote;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.base.util.DateUtil;
import com.liyz.cloud.common.model.bo.member.UserAdminInfoBO;
import com.liyz.cloud.common.model.bo.member.UserRegisterBO;
import com.liyz.cloud.common.model.constant.member.MemberConstant;
import com.liyz.cloud.common.model.constant.member.MemberServiceCodeEnum;
import com.liyz.cloud.common.model.exception.mmeber.RemoteMemberServiceException;
import com.liyz.cloud.service.member.config.MemberSnowflakeConfig;
import com.liyz.cloud.service.member.model.UserAdminInfoDO;
import com.liyz.cloud.service.member.service.UserAdminInfoService;
import com.liyz.cloud.service.member.util.MemberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/3 17:35
 */
@Slf4j
@Service
public class RemoteUserAdminInfoService {

    @Autowired
    UserAdminInfoService userAdminInfoService;
    @Autowired
    MemberSnowflakeConfig memberSnowflakeConfig;
    @Autowired
    RemoteUserSmsService remoteUserSmsService;

    /**
     * 用户注册
     *
     * @param userRegisterBO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public UserAdminInfoBO register(UserRegisterBO userRegisterBO) {
        int type = MemberUtil.checkMobileEmail(userRegisterBO.getLoginName(), MemberServiceCodeEnum.MobileEmailNonMatch);
        UserAdminInfoDO param = new UserAdminInfoDO();
        param.setLoginName(userRegisterBO.getLoginName());
        int count = userAdminInfoService.selectCount(param);
        if (count > 0) {
            throw new RemoteMemberServiceException(type == 1 ? MemberServiceCodeEnum.MobileExsist : MemberServiceCodeEnum.EmailExsist);
        }
        //校验验证码
        if (!remoteUserSmsService.validateSmsCode(MemberConstant.SMS_REGISTER_TYPE, userRegisterBO.getLoginName(),
                userRegisterBO.getVerificationCode())) {
            throw new RemoteMemberServiceException(type == 1 ? CommonCodeEnum.MobileCodeError : CommonCodeEnum.EmailCodeError);
        }
        param = CommonConverterUtil.beanCopy(userRegisterBO, UserAdminInfoDO.class);
        param.setUserId(memberSnowflakeConfig.getId());
        param.setEmail(type == 2 ? param.getLoginName() : "812672598@qq.com");
        param.setMobile(type == 1 ? param.getLoginName() : "15988654731");
        param.setRole("user");
        param.setRegTime(new Date());
        count = userAdminInfoService.save(param);
        if (count == 0) {
            throw new RemoteMemberServiceException(MemberServiceCodeEnum.RegisterFail);
        }
        return CommonConverterUtil.beanCopy(param, UserAdminInfoBO.class);
    }

    /**
     * 根据登陆名查询用户信息
     *
     * @param loginName
     * @return
     */
    public JwtUserBO getByLoginName(String loginName) {
        UserAdminInfoDO userAdminInfoDO = userAdminInfoService.getByLoginName(loginName);
        return CommonConverterUtil.beanCopy(userAdminInfoDO, JwtUserBO.class);
    }

    /**
     * 通过用户id查询用户信息
     *
     * @param userId
     * @return
     */
    public UserAdminInfoBO getByUserId(Long userId) {
        UserAdminInfoDO userAdminInfoDO = userAdminInfoService.getById(userId);
        if (Objects.isNull(userAdminInfoDO)) {
            throw new RemoteMemberServiceException(CommonCodeEnum.NoData);
        }
        return CommonConverterUtil.beanCopy(userAdminInfoDO, UserAdminInfoBO.class);
    }

    /**
     * 分页查询用户信息
     *
     * @param page
     * @param size
     * @return
     */
    public PageInfo<UserAdminInfoBO> pageList(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<UserAdminInfoDO> doList = userAdminInfoService.listAll();
        PageInfo<UserAdminInfoDO> doPageInfo = new PageInfo<>(doList);
        PageInfo<UserAdminInfoBO> boPageInfo = CommonConverterUtil.PageTransform(doPageInfo, UserAdminInfoBO.class);
        return boPageInfo;
    }

    /**
     * 根据查询条件查询出一条用户信息
     *
     * @param userAdminInfoBO
     * @return
     */
    public UserAdminInfoBO getByCondition(UserAdminInfoBO userAdminInfoBO) {
        UserAdminInfoDO userAdminInfoDO = null;
        try {
            userAdminInfoDO = userAdminInfoService.getOne(CommonConverterUtil.beanCopy(userAdminInfoBO, UserAdminInfoDO.class));
        } catch (Exception e) {
            log.error("出错啦", e);
        }
        return CommonConverterUtil.beanCopy(userAdminInfoDO, UserAdminInfoBO.class);
    }

    /**
     * 获取登陆时间-登陆时调用
     *
     * @param userId
     * @param ip
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Date loginTime(Long userId, String ip) {
        Date loginTime = kickDownLine(userId);
        return loginTime;
    }

    /**
     * 踢下线
     *
     * @param userId
     * @return
     */
    public Date kickDownLine(Long userId) {
        UserAdminInfoBO userAdminInfoBO = new UserAdminInfoBO();
        userAdminInfoBO.setUserId(userId);
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        LocalDateTime localDateTime = DateUtil.minusTime(nowLocalDateTime, 10, ChronoUnit.SECONDS);
        Date tokenTime = DateUtil.convertLocalDateTimeToDate(localDateTime);
        userAdminInfoBO.setWebTokenTime(tokenTime);
        userAdminInfoService.updateById(CommonConverterUtil.beanCopy(userAdminInfoBO, UserAdminInfoDO.class));
        return DateUtil.convertLocalDateTimeToDate(nowLocalDateTime);
    }
}
