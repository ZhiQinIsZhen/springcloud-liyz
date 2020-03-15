package com.liyz.cloud.service.member.remote;

import com.alibaba.fastjson.JSON;
import com.liyz.cloud.common.base.enums.CommonCodeEnum;
import com.liyz.cloud.common.model.bo.member.ImageBO;
import com.liyz.cloud.common.model.bo.member.SmsInfoBO;
import com.liyz.cloud.common.model.bo.sms.EmailMessageBO;
import com.liyz.cloud.common.model.constant.member.MemberConstant;
import com.liyz.cloud.common.model.constant.member.MemberServiceCodeEnum;
import com.liyz.cloud.common.model.exception.mmeber.RemoteMemberServiceException;
import com.liyz.cloud.common.redisson.service.RedissonService;
import com.liyz.cloud.service.member.constant.RedisKeyConstant;
import com.liyz.cloud.service.member.fegin.FeignSendMsgService;
import com.liyz.cloud.service.member.service.KafkaService;
import com.liyz.cloud.service.member.service.UserInfoService;
import com.liyz.cloud.service.member.util.ImageCodeUtil;
import com.liyz.cloud.service.member.util.MemberUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;
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
    @Autowired
    KafkaService kafkaService;

    /**
     * 发送信息
     *
     * @param smsInfoBO
     * @return
     */
    public boolean message(SmsInfoBO smsInfoBO) {
        int type = MemberUtil.checkMobileEmail(smsInfoBO.getAddress(), MemberServiceCodeEnum.AddressNonMatch);
        //验证图形验证码
        if (!validateImageCode(smsInfoBO.getImageToken(), smsInfoBO.getImageCode())) {
            throw new RemoteMemberServiceException(CommonCodeEnum.ImageCodeError);
        }
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
            long remainTimeToLive = redissonService.remainTimeToLive(smsCodeKey);
            if (remainTimeToLive > 9 * 60 * 1000) {
                throw new RemoteMemberServiceException(type == 1 ? MemberServiceCodeEnum.SmsEmailSendAfter : MemberServiceCodeEnum.SmsEmailSendAfter);
            }
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
        redissonService.setValueExpire(smsCodeKey, smsCode, 10L, TimeUnit.MINUTES);
        log.info("*********sms send******user:{},smsCode:{}", smsInfoBO.getAddress(), smsCode);
        if (type == 2) {
            EmailMessageBO emailMessageBO = new EmailMessageBO();
            emailMessageBO.setCode(smsInfoBO.getSmsType());
            emailMessageBO.setAddress(smsInfoBO.getAddress());
            emailMessageBO.setSubject(MemberConstant.REGISTER_SUBJECT);
            emailMessageBO.setLocale(StringUtils.isBlank(smsInfoBO.getLocale()) ? MemberConstant.ZH_CN : smsInfoBO.getLocale());
            emailMessageBO.setParams(Arrays.asList(smsCode));
            /*Result<Boolean> result = feignSendMsgService.email(emailMessageBO);
            if (!CommonCodeEnum.success.getCode().equals(result.getCode())) {
                log.error("sms send error, code:{}, message:{}", result.getCode(), result.getMessage());
                return false;
            }*/
            //kafka发送消息
            kafkaService.sendSms(JSON.toJSONString(emailMessageBO));
        }
        return true;
    }

    /**
     * 验证短信/邮件验证码是否正确
     *
     * @param smsType
     * @param address
     * @param verificationCode
     * @return
     */
    public boolean validateSmsCode(Integer smsType, String address, String verificationCode) {
        if (Objects.isNull(smsType) || StringUtils.isBlank(address) || StringUtils.isBlank(verificationCode)) {
            return false;
        }
        String smsCodeKey = RedisKeyConstant.getSmsCodeKey(smsType.toString(), address);
        if (verificationCode.equals(redissonService.getValue(smsCodeKey))) {
            return true;
        }
        return false;
    }

    /**
     * 生成验证码
     *
     * @return
     */
    public ImageBO imageCode() throws NoSuchAlgorithmException, IOException {
        String imageCode = ImageCodeUtil.generateVerifyCode(4);
        log.info("************生成的图形验证码是：{}", imageCode);
        //生成加密token
        String token = MemberUtil.encodeMD5(imageCode + new Timestamp(System.currentTimeMillis()));
        String key = RedisKeyConstant.getImageTokenKey(token);
        redissonService.setValueExpire(key, imageCode, 5, TimeUnit.MINUTES);
        int w = 100, h = 39;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageCodeUtil.outputImage(w, h, out, imageCode);
        byte[] bytes = out.toByteArray();
        String imageBase64 = java.util.Base64.getEncoder().encodeToString(bytes);
        ImageBO imageBO = new ImageBO();
        imageBO.setImageToken(token);
        imageBO.setImageBase64(imageBase64);
        return imageBO;
    }

    /**
     * 验证验证码
     *
     * @param token
     * @param imageCode
     */
    public boolean validateImageCode(String token, String imageCode) {
        if (StringUtils.isBlank(token) || StringUtils.isBlank(imageCode)) {
            return false;
        }
        String key = RedisKeyConstant.getImageTokenKey(token);
        if (imageCode.equals(redissonService.getValue(key))) {
            return true;
        }
        return false;
    }

}
