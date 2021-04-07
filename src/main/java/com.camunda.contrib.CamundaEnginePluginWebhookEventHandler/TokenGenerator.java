package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;

// TODO: unit tests
public interface TokenGenerator {
    String generateToken(WebhookHistoryConfig config);
}
