package com.coders.commaauthentication.domain.oauth2.converter;

import com.coders.commaauthentication.domain.oauth2.jwt.AppleClientSecretGenerator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Component
@AllArgsConstructor
public class AppleOAuth2AuthorizationCodeGrantRequestEntityConverter extends OAuth2AuthorizationCodeGrantRequestEntityConverter {

    private static final String CLIENT_SECRET_KEY = "client_secret";
    private static final String APPLE_REGISTRATION_ID = "apple";

    private final AppleClientSecretGenerator appleClientSecretGenerator;

    @Override
    protected MultiValueMap<String, String> createParameters(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        var clientRegistrationId = authorizationCodeGrantRequest.getClientRegistration().getRegistrationId();
        if (APPLE_REGISTRATION_ID.equalsIgnoreCase(clientRegistrationId)) {
            var encryptedPrivateKey = appleClientSecretGenerator.createClientSecret();
            var parameter = super.createParameters(authorizationCodeGrantRequest);
            parameter.put(CLIENT_SECRET_KEY, List.of(encryptedPrivateKey));
            return parameter;
        }
        return super.createParameters(authorizationCodeGrantRequest);
    }
}
