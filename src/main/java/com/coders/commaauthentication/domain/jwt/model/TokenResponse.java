package com.coders.commaauthentication.domain.jwt.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    private String accessToken;
    private String refreshToken;

    public TokenResponse() {
    }
}