FROM maven:3.6.3-openjdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM camunda/camunda-bpm-platform:7.14.0 as final

RUN rm -r webapps/camunda-invoice
RUN rm -r webapps/h2
RUN rm -r webapps/examples
# TODO: handle different versions of plugin
COPY --chown=camunda:camunda --from=build /home/app/target/ /camunda/lib/
COPY --chown=camunda:camunda bpm-platform.xml /camunda/conf/bpm-platform.xml