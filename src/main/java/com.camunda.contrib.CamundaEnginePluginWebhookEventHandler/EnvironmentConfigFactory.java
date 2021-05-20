package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;

import java.util.HashMap;

public class EnvironmentConfigFactory implements WebhookHistoryConfigFactory {

    // TODO: write more tests around below and cleanup
    @Override
    public WebhookHistoryConfig createConfig() {
        String webhookMap = System.getenv("WEBHOOK_ENDPOINT_MAP");
        HashMap<String, String[]> webhookEndpointMap = null;
        if (webhookMap != null && !webhookMap.trim().isBlank()) {
            webhookEndpointMap = new HashMap<>();
            for (String map : webhookMap.split(";")) {
                String[] splitMap = map.split("=");
                String url = splitMap[0];
                String[] taskDefinitionKeys = splitMap[1].split(",");
                webhookEndpointMap.put(url, taskDefinitionKeys);
            }
        }

        return new WebhookHistoryConfig(
                System.getenv("JWT_ISSUER"),
                System.getenv("JWT_SECRET"),
                System.getenv("WEBHOOK_BASE_URL"),
                webhookEndpointMap);
    }
}
