package com.liyz.cloud.service.member.remote;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.base.util.DateUtil;
import com.liyz.cloud.common.model.bo.member.UserInfoBO;
import com.liyz.cloud.common.model.bo.member.UserRegisterBO;
import com.liyz.cloud.common.model.constant.member.MemberConstant;
import com.liyz.cloud.common.model.constant.member.MemberEnum;
import com.liyz.cloud.common.model.constant.member.MemberServiceCodeEnum;
import com.liyz.cloud.common.model.exception.mmeber.RemoteMemberServiceException;
import com.liyz.cloud.service.member.config.MemberSnowflakeConfig;
import com.liyz.cloud.service.member.model.UserInfoDO;
import com.liyz.cloud.service.member.service.UserInfoService;
import com.liyz.cloud.service.member.service.UserLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.liyz.cloud.common.model.constant.common.CommonConstant.EMAILREG;
import static com.liyz.cloud.common.model.constant.common.CommonConstant.PHONEREG;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/3 17:35
 */
@Slf4j
@Service
public class RemoteUserInfoService {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    MemberSnowflakeConfig memberSnowflakeConfig;
    @Autowired
    UserLoginLogService userLoginLogService;

    /**
     * 用户注册
     *
     * @param userRegisterBO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public UserInfoBO register(UserRegisterBO userRegisterBO) {
        int type;
        if (matchMobile(userRegisterBO.getLoginName())) {
            type = 1;
        } else if (matchEmail(userRegisterBO.getLoginName())){
            type = 2;
        } else {
            throw new RemoteMemberServiceException(MemberServiceCodeEnum.MobileEmailNonMatch);
        }
        UserInfoDO param = new UserInfoDO();
        param.setLoginName(userRegisterBO.getLoginName());
        int count = userInfoService.selectCount(param);
        if (count > 0) {
            throw new RemoteMemberServiceException(type == 1 ? MemberServiceCodeEnum.MobileExsist : MemberServiceCodeEnum.EmailExsist);
        }
        //校验验证码
        param = CommonConverterUtil.beanCopy(userRegisterBO, UserInfoDO.class);
        param.setUserId(memberSnowflakeConfig.getId());
        param.setEmail(type == 1 ? param.getLoginName() : "812672598@qq.com");
        param.setMobile(type == 2 ? param.getLoginName() : "15988654731");
        param.setRegTime(new Date());
        count = userInfoService.save(param);
        if (count == 0) {
            throw new RemoteMemberServiceException(MemberServiceCodeEnum.RegisterFail);
        }
        userLoginLogService.save(param.getUserId(), userRegisterBO.getIp(), MemberConstant.REGISTER_TYPE,
                userRegisterBO.getDeviceEnum().getDevice());
        return CommonConverterUtil.beanCopy(param, UserInfoBO.class);
    }

    /**
     * 根据登陆名查询用户信息
     *
     * @param loginName
     * @return
     */
    public JwtUserBO getByLoginName(String loginName) {
        UserInfoDO userInfoDO = null;
        try {
            UserInfoDO param = new UserInfoDO();
            param.setLoginName(loginName);
            userInfoDO = userInfoService.getOne(param);
        } catch (Exception e) {
            log.error("出错啦", e);
        }
        return CommonConverterUtil.beanCopy(userInfoDO, JwtUserBO.class);
    }

    /**
     * 通过用户id查询用户信息
     *
     * @param userId
     * @return
     */
    public UserInfoBO getByUserId(Long userId) {
        UserInfoDO userInfoDO = userInfoService.getById(userId);
        if (Objects.isNull(userInfoDO)) {
            throw new RemoteMemberServiceException(CommonCodeEnum.NoData);
        }
        return CommonConverterUtil.beanCopy(userInfoDO, UserInfoBO.class);
    }

    /**
     * 分页查询用户信息
     *
     * @param page
     * @param size
     * @return
     */
    public PageInfo<UserInfoBO> pageList(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<UserInfoDO> doList = userInfoService.listAll();
        PageInfo<UserInfoDO> doPageInfo = new PageInfo<>(doList);
        PageInfo<UserInfoBO> boPageInfo = CommonConverterUtil.PageTransform(doPageInfo, UserInfoBO.class);
        return boPageInfo;
    }

    /**
     * 根据查询条件查询出一条用户信息
     *
     * @param userInfoBO
     * @return
     */
    public UserInfoBO getByCondition(UserInfoBO userInfoBO) {
        UserInfoDO userInfoDO = null;
        try {
            userInfoDO = userInfoService.getOne(CommonConverterUtil.beanCopy(userInfoBO, UserInfoDO.class));
        } catch (Exception e) {
            log.error("出错啦", e);
        }
        return CommonConverterUtil.beanCopy(userInfoDO, UserInfoBO.class);
    }

    /**
     * 获取登陆时间-登陆时调用
     *
     * @param userId
     * @param ip
     * @param deviceEnum
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Date loginTime(Long userId, String ip, MemberEnum.DeviceEnum deviceEnum) {
        Date loginTime = kickDownLine(userId, deviceEnum);
        userLoginLogService.save(userId, ip, MemberConstant.LOGIN_TYPE, deviceEnum.getDevice());
        return loginTime;
    }

    /**
     * 踢下线
     *
     * @param userId
     * @param deviceEnum
     * @return
     */
    public Date kickDownLine(Long userId, MemberEnum.DeviceEnum deviceEnum) {
        UserInfoBO userInfoBO = new UserInfoBO();
        userInfoBO.setUserId(userId);
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        LocalDateTime localDateTime = DateUtil.minusTime(nowLocalDateTime, 10, ChronoUnit.SECONDS);
        Date tokenTime = DateUtil.convertLocalDateTimeToDate(localDateTime);
        if (MemberEnum.DeviceEnum.WEB == deviceEnum) {
            userInfoBO.setWebTokenTime(tokenTime);
        } else if (MemberEnum.DeviceEnum.MOBILE == deviceEnum) {
            userInfoBO.setAppTokenTime(tokenTime);
        } else {
            userInfoBO.setWebTokenTime(tokenTime);
            userInfoBO.setAppTokenTime(tokenTime);
        }
        userInfoService.updateById(CommonConverterUtil.beanCopy(userInfoBO, UserInfoDO.class));
        return DateUtil.convertLocalDateTimeToDate(nowLocalDateTime);
    }

    /**
     * 匹配手机
     *
     * @param mobile
     * @return
     */
    public static boolean matchMobile(String mobile) {
        Pattern p = Pattern.compile(PHONEREG);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 匹配邮箱
     *
     * @param email
     * @return
     */
    public static boolean matchEmail(String email) {
        Pattern p = Pattern.compile(EMAILREG);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
