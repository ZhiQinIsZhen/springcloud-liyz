package com.liyz.cloud.service.sms.consumer;

import com.alibaba.fastjson.JSON;
import com.liyz.cloud.common.model.bo.sms.EmailMessageBO;
import com.liyz.cloud.service.sms.remote.RemoteSendMsgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/14 17:06
 */
@Slf4j
@Service
public class SmsConsumer {

    @Autowired
    RemoteSendMsgService remoteSendMsgService;

    @KafkaListener(topics = "${cloud.topic.sms}")
    public void smsReceive(ConsumerRecord<String, String> record) {
        String payload = record.value();
        log.info("payload: {}, offset：{}", payload, record.offset());
        try {
            EmailMessageBO emailMessageBO = JSON.parseObject(payload, EmailMessageBO.class);
            remoteSendMsgService.email(emailMessageBO);
        } catch (Exception e) {
            log.error("error when readValue", e);
            return;
        }
    }
}
