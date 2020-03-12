package com.liyz.cloud.service.sms.remote;

import com.liyz.cloud.common.model.bo.sms.EmailMessageBO;
import com.liyz.cloud.common.model.constant.sms.SmsConstant;
import com.liyz.cloud.service.sms.config.EmailConfig;
import com.liyz.cloud.service.sms.model.MsgTemplateDO;
import com.liyz.cloud.service.sms.service.MsgTemplateService;
import com.liyz.cloud.service.sms.util.SmsCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/11 17:42
 */
@Slf4j
@Service
public class RemoteSendMsgService {

    @Autowired
    MsgTemplateService msgTemplateService;

    public void email(EmailMessageBO emailMessageBO) {
        MsgTemplateDO queryDO = new MsgTemplateDO();
        queryDO.setCode(emailMessageBO.getCode());
        queryDO.setLocale(emailMessageBO.getLocale());
        queryDO.setType(SmsConstant.MSG_EMAIL_TYPE);
        MsgTemplateDO msgTemplateDO = SmsCacheUtil.getMsgTemplate(queryDO);
        if (Objects.isNull(msgTemplateDO)) {
            msgTemplateDO = msgTemplateService.getOne(queryDO);
            if (Objects.isNull(msgTemplateDO)) {
                log.error("email template not exist, code:{},locale:{}", emailMessageBO.getCode(), emailMessageBO.getLocale());
                return;
            }
            SmsCacheUtil.pubMsgTemplate(msgTemplateDO);
        }
        emailMessageBO.setContent(msgTemplateDO.getContent());
        EmailConfig.sendSmtpEmail(emailMessageBO);
    }
}
