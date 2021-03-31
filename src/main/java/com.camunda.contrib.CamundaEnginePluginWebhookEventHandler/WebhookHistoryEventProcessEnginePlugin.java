package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.camunda.bpm.engine.impl.history.handler.CompositeDbHistoryEventHandler;
import org.camunda.bpm.engine.impl.history.handler.CompositeHistoryEventHandler;

public class WebhookHistoryEventProcessEnginePlugin implements ProcessEnginePlugin {

	@Override
	public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

		List<HistoryLevel> customHistoryLevels = processEngineConfiguration.getCustomHistoryLevels();
		if (customHistoryLevels == null) {
			customHistoryLevels = new ArrayList<HistoryLevel>();
			processEngineConfiguration.setCustomHistoryLevels(customHistoryLevels);
		}
		customHistoryLevels.add(WebhookHistoryLevel.getInstance());


		processEngineConfiguration.setHistoryEventHandler(new CompositeDbHistoryEventHandler(new WebhookHistoryEventHandler()));


		//processEngineConfiguration.setHistoryEventHandler(MyCustomHistoryEventHandler.getInstance());

	}

	@Override
	public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

	}

	@Override
	public void postProcessEngineBuild(ProcessEngine processEngine) {

	}

}
