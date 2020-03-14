package com.liyz.cloud.service.sms.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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

    @KafkaListener(topics = "${cloud.topic.sms}")
    public void smsReceive(ConsumerRecord<String, String> record) {
        String payload = record.value();
        log.info("payload: {}, offset：{}", payload, record.offset());
        try {
            log.info("receive success");
        } catch (Exception e) {
            log.error("error when readValue", e);
            return;
        }
    }
}
