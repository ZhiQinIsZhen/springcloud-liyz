package com.liyz.cloud.service.member.remote;

import com.liyz.cloud.common.base.Result.Result;
import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.common.model.bo.member.SmsInfoBO;
import com.liyz.cloud.common.model.bo.sms.EmailMessageBO;
import com.liyz.cloud.common.model.constant.member.MemberConstant;
import com.liyz.cloud.common.model.constant.member.MemberServiceCodeEnum;
import com.liyz.cloud.common.model.exception.mmeber.RemoteMemberServiceException;
import com.liyz.cloud.common.redisson.service.RedissonService;
import com.liyz.cloud.service.member.constant.RedisKeyConstant;
import com.liyz.cloud.service.member.fegin.FeignSendMsgService;
import com.liyz.cloud.service.member.service.UserInfoService;
import com.liyz.cloud.service.member.util.MemberUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/12 10:28
 */
@Slf4j
@Service
public class RemoteUserSmsService {

    @Autowired
    RedissonService redissonService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    FeignSendMsgService feignSendMsgService;

    /**
     * 发送信息
     *
     * @param smsInfoBO
     * @return
     */
    public boolean message(SmsInfoBO smsInfoBO) {
        int type;
        if (MemberUtil.matchMobile(smsInfoBO.getAddress())) {
            type = 1;
        } else if (MemberUtil.matchEmail(smsInfoBO.getAddress())){
            type = 2;
        } else {
            throw new RemoteMemberServiceException(MemberServiceCodeEnum.AddressNonMatch);
        }
        //todo 验证图形验证码
        validateImageCode(smsInfoBO);
        int count = userInfoService.loginNameCount(smsInfoBO.getAddress());
        if (count == 1 && (MemberConstant.SMS_REGISTER_TYPE == smsInfoBO.getSmsType()
                || MemberConstant.SMS_BIND_EMAIL_TYPE == smsInfoBO.getSmsType()
                || MemberConstant.SMS_BIND_MOBILE_TYPE == smsInfoBO.getSmsType())) {
            throw new RemoteMemberServiceException(type == 1 ? MemberServiceCodeEnum.MobileExsist : MemberServiceCodeEnum.EmailExsist);
        }
        if (count == 0 && (MemberConstant.SMS_LOGIN_TYPE == smsInfoBO.getSmsType()
                || MemberConstant.SMS_FIND_PWDTYPE == smsInfoBO.getSmsType()
                || MemberConstant.SMS_GOOGLE_TYPE == smsInfoBO.getSmsType()
                || MemberConstant.SMS_UPDATE_PWD_TYPE == smsInfoBO.getSmsType())) {
            throw new RemoteMemberServiceException(MemberServiceCodeEnum.UserNonExist);
        }
        String smsCodeKey = RedisKeyConstant.getSmsCodeKey(smsInfoBO.getSmsType().toString(), smsInfoBO.getAddress());
        String smsCode = redissonService.getValue(smsCodeKey);
        if (StringUtils.isNotBlank(smsCode)) {
            throw new RemoteMemberServiceException(type == 1 ? MemberServiceCodeEnum.SmsEmailSendAfter : MemberServiceCodeEnum.SmsEmailSendAfter);
        }
        //确认24h次数
        String timesKey = RedisKeyConstant.getSmsTimesKey(smsInfoBO.getAddress());
        long times = redissonService.getAndIncrement(timesKey);
        if (times == 1) {
            redissonService.expire(timesKey, 1, TimeUnit.DAYS);
        }
        if (times > 10) {
            throw new RemoteMemberServiceException(MemberServiceCodeEnum.SmsLimit);
        }
        //生成验证码
        smsCode = MemberUtil.randomInteger(6);
        redissonService.setValueExpire(smsCodeKey, smsCode, 1, TimeUnit.MINUTES);
        log.info("***************user:{},smsCode:{}", smsInfoBO.getAddress(), smsCode);
        if (type == 2) {
            EmailMessageBO emailMessageBO = new EmailMessageBO();
            emailMessageBO.setCode(smsInfoBO.getSmsType());
            emailMessageBO.setAddress(smsInfoBO.getAddress());
            emailMessageBO.setSubject(MemberConstant.REGISTER_SUBJECT);
            emailMessageBO.setLocale(StringUtils.isBlank(smsInfoBO.getLocale()) ? MemberConstant.ZH_CN : smsInfoBO.getLocale());
            emailMessageBO.setParams(Arrays.asList(smsCode));
            Result<Boolean> result = feignSendMsgService.email(emailMessageBO);
            if (!CommonCodeEnum.success.getCode().equals(result.getCode())) {
                log.error("sms send error, code:{}, message:{}", result.getCode(), result.getMessage());
                return false;
            }
        }
        return true;
    }

    public void validateImageCode(SmsInfoBO smsInfoBO) {

    }
}
