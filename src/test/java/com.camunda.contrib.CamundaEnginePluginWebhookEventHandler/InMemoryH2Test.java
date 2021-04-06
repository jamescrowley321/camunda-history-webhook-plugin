package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.lang.reflect.Field;

import java.util.Map;
import java.util.Random;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.junit.Assert.*;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class InMemoryH2Test {

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule();

    private static final String PROCESS_DEFINITION_KEY = "custom-history-event-handler";

    static {
        LogFactory.useSlf4jLogging(); // MyBatis
    }

    @SuppressWarnings({ "unchecked" })
    public static void updateEnv(String name, String val) throws NoSuchFieldException, IllegalAccessException {
        Map<String, String> env = System.getenv();
        Field field = env.getClass().getDeclaredField("m");
        field.setAccessible(true);
        ((Map<String, String>) field.get(env)).put(name, val);
    }

    private String getSigningKey() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Encoders.BASE64.encode(key.getEncoded());
    }

    @Before
    public void setup() {
        try {
            updateEnv("JWT_SECRET", getSigningKey());
            updateEnv("JWT_ISSUER", "telescope");
            updateEnv("WEBHOOK_BASE_URL", "http://localhost:8000");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        init(rule.getProcessEngine());
    }

    @Test
    @Deployment(resources = "process.bpmn")
    public void testHappyPath() {
        ProcessInstance processInstance = processEngine().getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY);

        assertThat(processInstance).task("Task_DoSomething");

        complete(task());

        assertThat(processInstance).isEnded();
    }

}