package com.liyz.cloud.service.member.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/3/14 16:51
 */
@Slf4j
//@Service
public class Test {

    @Value("${cloud.topic.sms}")
    private String topicSms;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(cron = "0/5 * * * * ?")
    public void testKafka() {
        log.info("start test task.......");
        long time = System.currentTimeMillis();
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicSms, String.valueOf(time));
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("failure send for topic {} with value {}", topicSms, time, throwable);
            }

            @Override
            public void onSuccess(SendResult<String, String> sendResult) {
                log.info("success send to topic {} with value {}", topicSms, time);
            }
        });
        log.info("end test task.......");
    }
}
