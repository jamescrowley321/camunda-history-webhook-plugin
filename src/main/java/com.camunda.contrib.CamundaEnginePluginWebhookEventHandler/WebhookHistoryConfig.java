package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;

public class WebhookHistoryConfig {

    private String jwtIssuer;
    private String jwtSecret;
    private String webhookBaseUrl;

    public WebhookHistoryConfig() {
    }

    public WebhookHistoryConfig(String jwtIssuer, String jwtSecret, String webhookBaseUrl) {
        this.jwtIssuer = jwtIssuer;
        this.jwtSecret = jwtSecret;
        this.webhookBaseUrl = webhookBaseUrl;
    }

    public String getJwtIssuer() {
        return jwtIssuer;
    }

    public void setJwtIssuer(String jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String getWebhookBaseUrl() {
        return webhookBaseUrl;
    }

    public void setWebhookBaseUrl(String webhookBaseUrl) {
        this.webhookBaseUrl = webhookBaseUrl;
    }

}


