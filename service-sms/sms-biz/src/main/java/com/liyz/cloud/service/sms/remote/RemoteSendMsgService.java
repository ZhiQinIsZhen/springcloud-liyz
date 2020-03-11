package com.liyz.cloud.service.sms.remote;

import com.liyz.cloud.common.model.bo.sms.EmailMessageBO;
import com.liyz.cloud.common.model.constant.sms.SmsConstant;
import com.liyz.cloud.service.sms.config.EmailConfig;
import com.liyz.cloud.service.sms.model.MsgTemplateDO;
import com.liyz.cloud.service.sms.service.MsgTemplateService;
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
        MsgTemplateDO msgTemplateDO = new MsgTemplateDO();
        msgTemplateDO.setCode(emailMessageBO.getCode());
        msgTemplateDO.setLocale(emailMessageBO.getLocale());
        msgTemplateDO.setType(SmsConstant.MSG_EMAIL_TYPE);
        msgTemplateDO = msgTemplateService.getOne(msgTemplateDO);
        if (Objects.isNull(msgTemplateDO)) {
            log.error("email template not exist, code:{},locale:{}", emailMessageBO.getCode(), emailMessageBO.getLocale());
            return;
        }
        emailMessageBO.setContent(msgTemplateDO.getContent());
        EmailConfig.sendSmtpEmail(emailMessageBO);
    }
}
