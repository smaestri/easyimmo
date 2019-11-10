package com.easyimmo.service;

import com.easyimmo.domain.Ad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.easyimmo.service.UserService.TOPIC;

@Service
public class UserListener {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserService userService;

    @KafkaListener(topics = TOPIC, groupId = "user", containerFactory = "kafkaListenerContainerFactory")
    public void consume(Ad ad) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", ad.toString()));

        switch (ad.getStatus()) {
            case PENDING:
                userService.processPending(ad);
                break;
            default:
                logger.info("Message not take into account : " + ad.getStatus().toString());
        }

    }

}
