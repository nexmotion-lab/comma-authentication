package com.coders.commaauthentication.domain.oauth2.userinfo;

import com.coders.commaauthentication.domain.user.Gender;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();

    public abstract String getNickName();

    public abstract LocalDate getBirthDate();

    public abstract Gender getGender();

    public abstract String getEmail();

}
