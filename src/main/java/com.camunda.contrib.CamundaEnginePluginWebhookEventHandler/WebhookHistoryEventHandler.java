package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;


import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;


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
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(serializedHistoryEvent, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(config.getWebhookBaseUrl())
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

    @Override
    public void handleEvents(List<HistoryEvent> historyEvents) {
        for (HistoryEvent historyEvent : historyEvents) {
            handleEvent(historyEvent);
        }
    }


}
