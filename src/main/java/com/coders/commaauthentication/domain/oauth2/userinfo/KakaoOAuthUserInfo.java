package com.coders.commaauthentication.domain.oauth2.userinfo;

import com.coders.commaauthentication.domain.user.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class KakaoOAuthUserInfo extends OAuth2UserInfo{

    public KakaoOAuthUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getNickName() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

        if (account == null) {
            return null;
        }

        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        if (profile == null) {
            return null;
        }

        return (String) profile.get("nickname");
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
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

        if (account == null) {
            return null;
        }

        String email = (String) account.get("email") + ":kakao";

        if (email == null) {
            return null;
        }

        return email;
    }


}
