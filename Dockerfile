FROM maven:3.6.3-openjdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM camunda/camunda-bpm-platform:7.14.0 as final

RUN rm -r webapps/camunda-invoice
RUN rm -r webapps/h2
RUN rm -r webapps/examples

COPY --chown=camunda:camunda --from=build /home/app/target/com.camunda.contrib.CamundaEnginePluginWebhookEventHandler-0.0.1-SNAPSHOT.jar /camunda/lib/camunda-engine-history-webhook-plugin-0.0.1-SNAPSHOT.jar