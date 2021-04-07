package com.camunda.contrib.CamundaEnginePluginWebhookEventHandler;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class SymmetricTokenGenerator implements TokenGenerator {
    @Override
    public String generateToken(WebhookHistoryConfig config) {
//        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        Key signingKey = new SecretKeySpec(Decoders.BASE64.decode(config.getJwtSecret()), signatureAlgorithm.getJcaName());

        // Let's set the JWT Claims
        // TODO: expiration and subject?
        // TODO: set subject of claim
        // TODO: handle JTI
        // TODO: handle subject
        JwtBuilder builder = Jwts.builder().setId("camundaWebhookHistoryPlugin")
                .setSubject("camundaWebhookHistoryPlugin")
                .setIssuedAt(now)
                .setIssuer(config.getJwtIssuer())
                .signWith(signingKey);

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
}

