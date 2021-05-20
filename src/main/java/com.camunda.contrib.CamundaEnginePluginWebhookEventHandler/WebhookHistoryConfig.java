package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;


import java.util.HashMap;

public class WebhookHistoryConfig {

    private final String jwtIssuer;
    private final String jwtSecret;
    private final String webhookBaseUrl;
    private final HashMap<String, String[]> webhookEndpointMap;

    public WebhookHistoryConfig(String jwtIssuer, String jwtSecret, String webhookBaseUrl, HashMap<String, String[]> webhookEndpointMap) {
        this.jwtIssuer = jwtIssuer;
        this.jwtSecret = jwtSecret;
        this.webhookBaseUrl = webhookBaseUrl;
        this.webhookEndpointMap = webhookEndpointMap;
    }

    public String getJwtIssuer() {
        return jwtIssuer;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public String getWebhookBaseUrl() {
        return webhookBaseUrl;
    }

    public HashMap<String, String[]> getWebhookEndpointMap() {
        return webhookEndpointMap;
    }
}


