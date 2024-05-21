package com.coders.commaauthentication.domain.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {

    private String refreshToken;
    private String accessToken;


    public TokenRequest() {
    }
}
