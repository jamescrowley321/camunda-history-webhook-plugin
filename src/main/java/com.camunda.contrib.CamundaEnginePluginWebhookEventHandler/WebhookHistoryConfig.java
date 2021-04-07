package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;

public class WebhookHistoryConfig {

    private final String jwtIssuer;
    private final String jwtSecret;
    private final String webhookBaseUrl;

    public WebhookHistoryConfig(String jwtIssuer, String jwtSecret, String webhookBaseUrl) {
        this.jwtIssuer = jwtIssuer;
        this.jwtSecret = jwtSecret;
        this.webhookBaseUrl = webhookBaseUrl;
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
}


