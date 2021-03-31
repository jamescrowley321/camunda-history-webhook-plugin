package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;

import java.util.List;
import java.util.logging.Logger;

import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;


public class WebhookHistoryEventHandler implements HistoryEventHandler {

	private final Logger LOGGER = Logger.getLogger(WebhookHistoryEventHandler.class.getName());

	private static final WebhookHistoryEventHandler INSTANCE = new WebhookHistoryEventHandler();

	public static WebhookHistoryEventHandler getInstance(){
		return INSTANCE;
	}

	@Override
	public void handleEvent(HistoryEvent historyEvent) {

		LOGGER.info("----- HISTORY EVENT PRODUCED: "+ historyEvent.toString());
		// TODO: this is where webook action starts

	}

	@Override
	public void handleEvents(List<HistoryEvent> historyEvents) {
		for (HistoryEvent historyEvent : historyEvents) {
			handleEvent(historyEvent);
			}
	}


}
