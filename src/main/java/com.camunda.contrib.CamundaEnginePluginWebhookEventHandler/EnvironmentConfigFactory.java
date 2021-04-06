package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;

public class EnvironmentConfigFactory implements WebhookHistoryConfigFactory {

    @Override
    public WebhookHistoryConfig createConfig() {
        return new WebhookHistoryConfig(
                System.getenv("JWT_ISSUER"),
                System.getenv("JWT_SECRET"),
                System.getenv("WEBHOOK_BASE_URL")
        );
    }
}
