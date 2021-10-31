package com.stressthem.app.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.stressthem.app.services.interfaces.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AdviceServiceImpl implements AdviceService {

    @Value("${advice.api.url}")
    private String ADVICE_API_URL;

    private RestTemplate restTemplate;

    private Gson gson;

    @Autowired
    public AdviceServiceImpl(RestTemplate restTemplate, Gson gson) {
        this.restTemplate = restTemplate;
        this.gson = gson;
    }


    @Override
    public String getRandomAdvice() {
        String json = restTemplate.getForObject(ADVICE_API_URL, String.class);
        return this.gson.fromJson(json, JsonObject.class).get("slip").getAsJsonObject().get("advice").toString();
    }
}
