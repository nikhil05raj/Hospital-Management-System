package com.example.HMS.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class webhookService{

    private final RestTemplate restTemplate;

    public webhookService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public void sendWebhook(String url, Enum eventType, Map<String, Object>payload) {
        payload.put("event",eventType.name());

        try {
            restTemplate.postForObject(url, payload, String.class);
            System.out.println("Webhooks sent successfully to : "+url);
        }
        catch (Exception e) {
            System.err.println("Failed to send webhooks : " + e.getMessage());
        }
    }
}
