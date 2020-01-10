package com.liyz.cloud.service.member.remote;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.common.base.remote.bo.JwtUserBO;
import com.liyz.cloud.common.base.util.CommonConverterUtil;
import com.liyz.cloud.common.base.util.DateUtil;
import com.liyz.cloud.common.model.bo.member.UserInfoBO;
import com.liyz.cloud.common.model.constant.member.MemberEnum;
import com.liyz.cloud.common.model.exception.mmeber.RemoteMemberServiceException;
import com.liyz.cloud.service.member.model.UserInfoDO;
import com.liyz.cloud.service.member.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class RemoteUserInfoService {

    @Autowired
    UserInfoService userInfoService;

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

    public UserInfoBO getByUserId(Long userId) {
        UserInfoDO userInfoDO = userInfoService.getById(userId);
        if (Objects.isNull(userInfoDO)) {
            throw new RemoteMemberServiceException(CommonCodeEnum.NoData);
        }
        return CommonConverterUtil.beanCopy(userInfoDO, UserInfoBO.class);
    }

    public PageInfo<UserInfoBO> pageList(Integer page, Integer size) {
        if (!Objects.isNull(page)) {
            throw new RemoteMemberServiceException(CommonCodeEnum.NoData);
        }
        PageHelper.startPage(page, size);
        List<UserInfoDO> doList = userInfoService.listAll();
        PageInfo<UserInfoDO> doPageInfo = new PageInfo<>(doList);
        PageInfo<UserInfoBO> boPageInfo = CommonConverterUtil.PageTransform(doPageInfo, UserInfoBO.class);
        return boPageInfo;
    }

    public UserInfoBO getByCondition(UserInfoBO userInfoBO) {
        UserInfoDO userInfoDO = null;
        try {
            userInfoDO = userInfoService.getOne(CommonConverterUtil.beanConverter(userInfoBO, UserInfoDO.class));
        } catch (Exception e) {
            log.error("出错啦", e);
        }
        return CommonConverterUtil.beanConverter(userInfoDO, UserInfoBO.class);
    }

    public Date kickDownLine(Long userId, MemberEnum.DeviceEnum deviceEnum) {
        UserInfoBO userInfoBO = new UserInfoBO();
        userInfoBO.setUserId(userId);
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        LocalDateTime localDateTime = DateUtil.minusTime(nowLocalDateTime, 10, ChronoUnit.MINUTES);
        Date tokenTime = DateUtil.convertLocalDateTimeToDate(localDateTime);
        if (MemberEnum.DeviceEnum.WEB == deviceEnum) {
            userInfoBO.setWebTokenTime(tokenTime);
        } else if (MemberEnum.DeviceEnum.MOBILE == deviceEnum) {
            userInfoBO.setAppTokenTime(tokenTime);
        } else {
            userInfoBO.setWebTokenTime(tokenTime);
            userInfoBO.setAppTokenTime(tokenTime);
        }
        userInfoService.updateById(CommonConverterUtil.beanConverter(userInfoBO, UserInfoDO.class));
        return DateUtil.convertLocalDateTimeToDate(nowLocalDateTime);
    }
}
