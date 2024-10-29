package com.coders.commaauthentication.domain.oauth2.jwt;

import io.jsonwebtoken.Jwts;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.util.Date;

@Component
public class AppleClientSecretGenerator {



    @Value("${apple.key-id}")
    private String keyId;

    @Value("${apple.team-id}")
    private String teamId;

    @Value("${spring.security.oauth2.client.registration.apple.client-id}")
    private String clientId;

    @Value("${apple.private-key-path}")
    private Resource privateKeyResource;

    private PrivateKey getPrivateKey() throws Exception {
        try (PEMParser pemParser = new PEMParser(new InputStreamReader(privateKeyResource.getInputStream()))) {
            PrivateKeyInfo pemObject = (PrivateKeyInfo) pemParser.readObject();
            return new JcaPEMKeyConverter().getPrivateKey(pemObject);
        }
    }

    public String createClientSecret() {
        try {
            PrivateKey privateKey = getPrivateKey();
            long now = System.currentTimeMillis();

            return Jwts.builder()
                    .header()
                    .add("alg", "ES256")
                    .add("kid", keyId)
                    .and()
                    .signWith(privateKey)
                    .issuer(teamId)
                    .issuedAt(new Date(now))
                    .expiration(new Date(now + 10 * 60 * 1000))
                    .subject(clientId)
                    .audience().add("https://appleid.apple.com")
                    .and().compact();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Apple Client secret", e);
        }
    }
}
