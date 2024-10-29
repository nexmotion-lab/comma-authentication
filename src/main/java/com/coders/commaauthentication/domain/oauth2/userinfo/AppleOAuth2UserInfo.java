package com.coders.commaauthentication.domain.oauth2.userinfo;

import com.coders.commaauthentication.domain.user.Gender;

import java.time.LocalDate;
import java.util.Map;

public class AppleOAuth2UserInfo extends OAuth2UserInfo{

    public AppleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getNickName() {
        return null;
    }

    @Override
    public LocalDate getBirthDate() {
        return null;
    }

    @Override
    public Gender getGender() {
        return null;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
