package com.easyimmo.service;

import com.easyimmo.domain.Ad;
import com.easyimmo.domain.AdStatus;
import com.easyimmo.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Optional;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    protected static final String TOPIC = "ad";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String, Ad> kafkaTemplate;

    public void processPending(Ad ad) throws JsonProcessingException {
        //Check USER
        Optional<User> user = userRepository.findById(ad.getAuthorId());
        if (user.isPresent() && user.get().isActive()) {
            ad.setStatus(AdStatus.USER_VALIDATED);
            logger.info(String.format("USer validated"));
        } else {
            logger.info(String.format("USer not validated"));
            ad.setStatus(AdStatus.USER_NOT_VALIDATED);
        }

        this.sendMessage(ad);
    }

    public void sendMessage(Ad ad) throws JsonProcessingException {
        logger.info(String.format("#### -> Producing message -> %s", ad));
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