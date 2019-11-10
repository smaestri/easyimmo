package com.easyimmo.service;

import com.easyimmo.domain.Ad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.easyimmo.service.AdService.TOPIC;

@Service
public class AdListener {

    private static final Logger logger = LoggerFactory.getLogger(AdService.class);

    @Autowired
    private AdService adService;

    @KafkaListener(topics = TOPIC, groupId = "ad-service", containerFactory = "kafkaListenerContainerFactory")
    public void consume(Ad ad) throws Exception {
        logger.info(String.format("#### -> Consumed message -> %s", ad));
        switch (ad.getStatus()) {
            case USER_VALIDATED :
                adService.processUserValidated(ad.getId());
                break;
            case USER_NOT_VALIDATED :
                adService.processUserNotValidated(ad.getId());
                break;
            case MAIL_SENT :
                adService.processMailSent(ad.getId());
                break;
            default:
                logger.info("Message not take into account : " + ad.getStatus().toString());
        }
    }


}
