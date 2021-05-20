package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.logging.Logger;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * Test case starting an in-memory database-backed Process Engine.
 * TODO: write more tests
 */
public class InMemoryH2Test {

    private final Logger LOGGER = Logger.getLogger(InMemoryH2Test.class.getName());

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
            LOGGER.info("Setting up environment");

            String signingKey = getSigningKey();
            LOGGER.info("Signing key: " + signingKey);
            updateEnv("JWT_SECRET", signingKey);
            updateEnv("JWT_ISSUER", "testJwtIssuer");
            updateEnv("WEBHOOK_BASE_URL", "http://mockbin.org/bin/40100097-af54-413b-86de-8cbbdd4227b2");
            updateEnv("WEBHOOK_ENDPOINT_MAP", "http://mockbin.org/bin/f59a535a-8e60-409f-bc69-09cdf29ace75=Task_DoSomething;");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        init(rule.getProcessEngine());
    }

    @Test
    @Deployment(resources = "process.bpmn")
    public void testHappyPath() {
        ProcessInstance processInstance = processEngine()
                .getRuntimeService()
                .startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
        assertThat(processInstance).task("Task_DoSomething");

        complete(task());

        assertThat(processInstance).isEnded();
    }

}