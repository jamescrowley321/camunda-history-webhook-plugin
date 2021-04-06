package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;

import java.util.List;
import java.util.logging.Logger;

import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;


public class WebhookHistoryEventHandler implements HistoryEventHandler {

    private final Logger LOGGER = Logger.getLogger(WebhookHistoryEventHandler.class.getName());

//    private static final WebhookHistoryEventHandler INSTANCE = new WebhookHistoryEventHandler();
//
//    public static WebhookHistoryEventHandler getInstance() {
//        return INSTANCE;
//    }

    public WebhookHistoryConfigFactory getWebhookHistoryConfigFactory() {
        return webhookHistoryConfigFactory;
    }

    public void setWebhookHistoryConfigFactory(WebhookHistoryConfigFactory webhookHistoryConfigFactory) {
        this.webhookHistoryConfigFactory = webhookHistoryConfigFactory;
    }

    public WebhookHistoryConfigFactory webhookHistoryConfigFactory;

    public TokenGenerator getTokenGenerator() {
        return tokenGenerator;
    }

    public void setTokenGenerator(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    public TokenGenerator tokenGenerator;

    public WebhookHistoryEventHandler(WebhookHistoryConfigFactory webhookHistoryConfigFactory, SymmetricTokenGenerator symmetricTokenGenerator) {
        this.webhookHistoryConfigFactory = webhookHistoryConfigFactory;
        this.tokenGenerator = symmetricTokenGenerator;
    }

    @Override
    public void handleEvent(HistoryEvent historyEvent) {

        //TODO: inject dependencies
        WebhookHistoryConfig config = this.webhookHistoryConfigFactory.createConfig();
        String token = this.tokenGenerator.generateToken(config);

        //TODO: make http request

        LOGGER.info("----- HISTORY EVENT PRODUCED: " + historyEvent.toString());
        // TODO: this is where webook action starts

    }

    @Override
    public void handleEvents(List<HistoryEvent> historyEvents) {
        for (HistoryEvent historyEvent : historyEvents) {
            handleEvent(historyEvent);
        }
    }


}
