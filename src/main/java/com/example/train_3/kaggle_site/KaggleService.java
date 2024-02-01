package com.example.train_3.kaggle_site;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;


//@Service
public class KaggleService {

    @Value("${kaggle.api.url}")
    private String kaggleApiUrl;

    @Value("${kaggle.api.key}")
    private String kaggleApiKey;


    private final RestTemplate restTemplate;

    public KaggleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String downloadImage(String imageUrl) {
        // Kaggle API orqali rasmni yuklash
        String downloadUrl = kaggleApiUrl + "/downloadImage?imageUrl=" + imageUrl;
        return restTemplate.getForObject(downloadUrl, String.class);
    }

}
