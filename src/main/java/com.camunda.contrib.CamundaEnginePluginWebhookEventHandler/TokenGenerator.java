package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;

// TODO: implement interface and tests
public interface TokenGenerator {
    public String generateToken(WebhookHistoryConfig config);
}
