package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.camunda.bpm.engine.impl.history.event.HistoricTaskInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WebhookHistoryEventHandler implements HistoryEventHandler {

    private final Logger LOGGER = Logger.getLogger(WebhookHistoryEventHandler.class.getName());

    public WebhookHistoryConfigFactory webhookHistoryConfigFactory;

    public TokenGenerator tokenGenerator;

    public WebhookHistoryEventHandler(WebhookHistoryConfigFactory webhookHistoryConfigFactory, SymmetricTokenGenerator symmetricTokenGenerator) {
        this.webhookHistoryConfigFactory = webhookHistoryConfigFactory;
        this.tokenGenerator = symmetricTokenGenerator;
    }

    @Override
    public void handleEvent(HistoryEvent historyEvent) {
        WebhookHistoryConfig config = this.webhookHistoryConfigFactory.createConfig();
        String token = this.tokenGenerator.generateToken(config);

        LOGGER.info("----- HISTORY EVENT PRODUCED: " + historyEvent.toString());
        String serializedHistoryEvent;
        try {
            serializedHistoryEvent = new ObjectMapper().writeValueAsString(historyEvent);
            LOGGER.info("History Event: " + serializedHistoryEvent);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.SEVERE, "Serialization of history event failed");
            return;
        }
        // TODO: this is where webhook action starts
        // TODO: make some sort of singleton for the client
        // TODO: support different endpoints for different tasks
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(serializedHistoryEvent, MediaType.parse("application/json"));
        AtomicReference<String> url = new AtomicReference<>("");

        // TODO: make more efficient
        if (isMappedToEventTaskDefinition(historyEvent, config)) {
            LOGGER.info("Found getWebhookEndpointMap");
            String taskDefinitionKey = ((HistoricTaskInstanceEventEntity) historyEvent).getTaskDefinitionKey();
            config.getWebhookEndpointMap().forEach((k, v) -> {
                if (Arrays.asList(v).contains(taskDefinitionKey)) {
                    url.set(k);
                }
            });
        } else {
            LOGGER.info("Failed to find getWebhookEndpointMap");
            url.set(config.getWebhookBaseUrl());
        }
        Request request = new Request.Builder()
                .url(url.get())
                .post(body)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        try {
            Response response = client.newCall(request).execute();
            LOGGER.info("----- HISTORY EVENT WEBHOOK FIRED " + response.toString());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Webhook failed", e);
        }
    }

    private boolean isMappedToEventTaskDefinition(HistoryEvent historyEvent, WebhookHistoryConfig config) {
        // TODO: find a way to check type
        return config.getWebhookEndpointMap() != null
                && !config.getWebhookEndpointMap().isEmpty()
//                && historyEvent.toString().contains("taskDefinitionKey");
                && historyEvent instanceof HistoricTaskInstanceEventEntity;
    }

    @Override
    public void handleEvents(List<HistoryEvent> historyEvents) {
        for (HistoryEvent historyEvent : historyEvents) {
            handleEvent(historyEvent);
        }
    }


}
