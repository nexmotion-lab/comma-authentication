package com.coders.commaauthentication.domain.oauth2.userinfo;

import com.coders.commaauthentication.domain.user.Gender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
public class NaverOAuth2UserInfo extends OAuth2UserInfo{

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }
        return (String) response.get("id");
    }

    @Override
    public String getNickName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        String name = (String) response.get("name");

        if (name == null) {
            return null;
        }


        log.info(name);
        return name;


    }

    @Override
    public LocalDate getBirthDate() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        String birthyear = (String) response.get("birthyear");
        String birthday = (String) response.get("birthday");

        if (birthyear == null || birthday == null) {
            return null;
        }

        return convertToLocalDate(birthyear, birthday);
    }

    public LocalDate convertToLocalDate(String year, String monthday) {
        String birthdate = year + "-" + monthday;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(birthdate, formatter);
    }

    @Override
    public Gender getGender() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        String gender = (String) response.get("gender");

        if (gender.equals("F")) {
            return Gender.WOMAN;
        } else if (gender.equals("M")) {
            return Gender.MAN;
        }

        return Gender.N;
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        String email = (String) response.get("email");

        if (email == null) {
            return null;
        }

        return email;
    }

}
