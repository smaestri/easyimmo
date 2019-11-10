package com.easyimmo.service;

import com.easyimmo.domain.Ad;
import com.easyimmo.domain.AdStatus;
import com.easyimmo.domain.MailTracking;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;

@Service
public class MailingService {

    private final Logger logger = LoggerFactory.getLogger(MailingService.class);
    protected static final String TOPIC = "ad";

    @Autowired
    private KafkaTemplate<String, Ad> kafkaTemplate;

    public void processUserValidated(Ad ad) throws JsonProcessingException {
        //send mail
        MailTracking mail = new MailTracking();
        mail.setSendingDate(new Date());
        mail.setUserId(ad.getAuthorId());

        // send event mail_sent
        ad.setStatus(AdStatus.MAIL_SENT);
        this.sendMessage(ad);
        logger.info(String.format("process finished"));
    }

    public void sendMessage(Ad ad) throws JsonProcessingException {
        logger.info(String.format("#### -> Producing message -> %s", ad.toString()));
        ListenableFuture<SendResult<String, Ad>> future = this.kafkaTemplate.send(TOPIC, ad);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Ad>>() {

            @Override
            public void onSuccess(SendResult<String, Ad> result) {
                System.out.println("Sent message=[" + ad.toString() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + ad.toString() + "] due to : " + ex.getMessage());
            }
        });

    }
}