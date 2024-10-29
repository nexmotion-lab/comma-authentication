package com.coders.commaauthentication.domain.oauth2.user;

import com.coders.commaauthentication.domain.user.Role;

public interface CustomUser {

    Role getRole();

    String getEmail();

    boolean isFirstLogin();
}
