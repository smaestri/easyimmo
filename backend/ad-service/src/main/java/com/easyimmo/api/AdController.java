package com.easyimmo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.easyimmo.domain.AdStatus;
import com.easyimmo.service.AdService;
import com.easyimmo.domain.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdController {

    @Autowired
    private AdService adService;

    @PostMapping(value = "/users/{userId}/ad")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void sendMessageToKafkaTopic(@PathVariable("userId") String userId, @RequestBody Ad adRequest) throws JsonProcessingException {
        Ad ad = new Ad();
        ad.setTitle(adRequest.getTitle());
        ad.setDescription(adRequest.getDescription());
        ad.setAuthorId(Integer.valueOf(userId));
        ad.setStatus(AdStatus.PENDING);
        this.adService.saveAd(ad);
    }

    @GetMapping(value = "/users/{userId}/ad")
    public List<Ad> getAds(@PathVariable("userId") String userId){
       return this.adService.getAds(userId);
    }
}
