package com.coders.commaauthentication.domain.oauth2.service;

import com.coders.commaauthentication.domain.oauth2.user.CustomOidcUser;
import com.coders.commaauthentication.domain.oauth2.OAuthAttributes;
import com.coders.commaauthentication.domain.user.Account;
import com.coders.commaauthentication.domain.user.SocialType;
import com.coders.commaauthentication.domain.user.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOidcUserService extends OidcUserService {

    private final AccountRepository accountRepository;

    private static final String APPLE = "apple";

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("OIDC 로그인 요청 진입");
        OidcUser oidcUser = super.loadUser(userRequest);

        SocialType socialType = getSocialType(userRequest.getClientRegistration().getRegistrationId());
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oidcUser.getAttributes();
        OAuthAttributes oAuthAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        Account user = getUser(oAuthAttributes, socialType);

        return new CustomOidcUser(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())),
                oidcUser.getIdToken(),
                oidcUser.getUserInfo(),
                oAuthAttributes.getNameAttributeKey(),
                user.getRole(),
                user.getEmail(),
                user.isFirstLogin()

        );

    }

    private SocialType getSocialType(String registrationId) {
        return SocialType.APPLE;
    }

    private Account getUser(OAuthAttributes attributes, SocialType socialType) {
        Account findUser = accountRepository.findByEmail(
                attributes.getOAuth2UserInfo().getEmail()
        ).orElse(null);

        if (findUser == null) {
            Account account = saveUser(attributes, socialType);
            account.setFirstLogin(true);
            return account;
        }

        return findUser;
    }

    private Account saveUser(OAuthAttributes attributes, SocialType socialType) {
        Account createdUser = attributes.toEntity(socialType, attributes.getOAuth2UserInfo());
        return accountRepository.save(createdUser);
    }

}
