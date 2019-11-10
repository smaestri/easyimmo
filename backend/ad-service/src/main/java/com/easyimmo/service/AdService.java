package com.easyimmo.service;

import com.easyimmo.domain.Ad;
import com.easyimmo.domain.AdStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AdService {

    private static final Logger logger = LoggerFactory.getLogger(AdService.class);
    protected static final String TOPIC = "ad";

    @Autowired
    private KafkaTemplate<String, Ad> kafkaTemplate;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private ObjectMapper mapper;

    public void processMailSent(Integer idAd) {
        saveAdWithStatus(idAd, AdStatus.CREATED);
    }

    public void processUserValidated(Integer idAd) {
        saveAdWithStatus(idAd, AdStatus.USER_VALIDATED);
    }

    public void processUserNotValidated(Integer adMessage) {
        saveAdWithStatus(adMessage, AdStatus.USER_NOT_VALIDATED);
    }

    private void saveAdWithStatus(Integer adId, AdStatus status) {
        Optional<Ad> ad = adRepository.findById(adId);
        if (ad.isPresent()) {
            ad.get().setStatus(status);
            adRepository.save(ad.get());
            logger.info(String.format("Process finished"));
        } else {
            logger.error(String.format("No ad found"));
        }
    }


    private void sendMessage(Ad ad) throws JsonProcessingException {
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

    @Transactional
    public void saveAd(Ad ad) throws JsonProcessingException {
        adRepository.save(ad);
        this.sendMessage(ad);
    }

    public List<Ad> getAds(String userId) {
        return adRepository.findByAuthorId(Integer.valueOf(userId));
    }
}