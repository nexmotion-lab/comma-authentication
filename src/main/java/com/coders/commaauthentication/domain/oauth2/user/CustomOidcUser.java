package com.coders.commaauthentication.domain.oauth2.user;

import com.coders.commaauthentication.domain.user.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Collection;

@Getter
public class CustomOidcUser extends DefaultOidcUser implements CustomUser{

    private Role role;
    private String email;
    private boolean isFirstLogin;

    public CustomOidcUser(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken,
                          OidcUserInfo userInfo, String nameAttributeKey,
                          Role role, String email, boolean isFirstLogin) {
        super(authorities, idToken, userInfo, nameAttributeKey);
        this.role = role;
        this.email = email;
        this.isFirstLogin = isFirstLogin;
    }
}
