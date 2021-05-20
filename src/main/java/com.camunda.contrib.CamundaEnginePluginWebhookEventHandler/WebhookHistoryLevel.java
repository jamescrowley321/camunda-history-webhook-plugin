package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;

import org.camunda.bpm.engine.impl.history.AbstractHistoryLevel;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.camunda.bpm.engine.impl.history.event.HistoryEventType;
import org.camunda.bpm.engine.impl.history.event.HistoryEventTypes;

import java.util.ArrayList;
import java.util.List;

public class WebhookHistoryLevel extends AbstractHistoryLevel implements HistoryLevel {

    public static final WebhookHistoryLevel INSTANCE = new WebhookHistoryLevel();

    private static List<HistoryEventType> eventTypes = new ArrayList<HistoryEventType>();

    static {
        eventTypes.add(HistoryEventTypes.PROCESS_INSTANCE_START);
        eventTypes.add(HistoryEventTypes.PROCESS_INSTANCE_END);
    }

    public static HistoryLevel getInstance() {
        return INSTANCE;
    }

    @Override
    public int getId() {
        return 666;
    }

    @Override
    public String getName() {
        return "webhookHistoryLevel";
    }

    @Override
    public boolean isHistoryEventProduced(HistoryEventType eventType, Object entity) {

        return eventTypes.contains(eventType);

    }

}
