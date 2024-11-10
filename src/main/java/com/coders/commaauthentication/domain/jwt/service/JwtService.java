package com.coders.commaauthentication.domain.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.coders.commaauthentication.domain.user.Account;
import com.coders.commaauthentication.domain.user.Role;
import com.coders.commaauthentication.domain.user.SocialType;
import com.coders.commaauthentication.domain.user.repository.AccountRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.CollationKey;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    public static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    public static final String EMAIL_CLAIM = "email";
    public static final String BEARER = "Bearer ";
    public static final String ROLE = "Role";

    private final AccountRepository accountRepository;

    public String createAccessToken(String email, Role role) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(EMAIL_CLAIM, email)
                .withClaim(ROLE, role.getKey())
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String createRefreshToken(String email) {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .withClaim(EMAIL_CLAIM, email)
                .sign(Algorithm.HMAC512(secretKey));
    }


    public void updateRefreshToken(String email, String refreshToken) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("일치하는 회원이 없습니다."));
        account.updateRefreshToken(refreshToken);
        accountRepository.save(account);
        log.info(account.toString());
    }
}
