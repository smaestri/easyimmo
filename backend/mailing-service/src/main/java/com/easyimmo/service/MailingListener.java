package com.easyimmo.service;

import com.easyimmo.domain.Ad;
import com.easyimmo.domain.AdStatus;
import com.easyimmo.domain.MailTracking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.easyimmo.service.MailingService.TOPIC;

@Service
public class MailingListener {

    private final Logger logger = LoggerFactory.getLogger(MailingListener.class);

    @Autowired
    MailingService mailingService;

    @KafkaListener(topics = TOPIC, groupId = "mailing", containerFactory = "kafkaListenerContainerFactory")
    public void consume(Ad ad) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", ad.toString()));
        switch (ad.getStatus()) {
            case USER_VALIDATED:
                mailingService.processUserValidated(ad);
                break;
            default:
                logger.info("Message not take into account : " + ad.getStatus().toString());
        }

    }


}
