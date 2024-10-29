package com.coders.commaauthentication.domain.oauth2.user;

import com.coders.commaauthentication.domain.user.Role;
import com.coders.commaauthentication.domain.user.SocialType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User implements CustomUser{

    private Role role;
    private String email;
    private boolean isFirstLogin;

    /**
     * Constructs a {@code DefaultOAuth2User} using the provided parameters.
     *
     * @param authorities      the authorities granted to the user
     * @param attributes       the attributes about the user
     * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
     *                         {@link #getAttributes()}
     */
    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey
                            ,Role role, String email, boolean isFirstLogin) {
        super(authorities, attributes, nameAttributeKey);
        this.role = role;
        this.email = email;
        this.isFirstLogin = isFirstLogin;
    }
}
