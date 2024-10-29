package com.coders.commaauthentication.domain.oauth2.handler;

import com.coders.commaauthentication.domain.jwt.service.JwtService;
import com.coders.commaauthentication.domain.oauth2.user.CustomOAuth2User;
import com.coders.commaauthentication.domain.oauth2.user.CustomOidcUser;
import com.coders.commaauthentication.domain.oauth2.user.CustomUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("로그인 성공");
        CustomUser user = (CustomUser) authentication.getPrincipal();

        String url = loginSuccess(user);

        if (user.isFirstLogin()) response.sendRedirect("comma://firstLogin" + url);
        else response.sendRedirect("comma://home" + url);
    }


    private String loginSuccess(CustomUser oAuth2User) throws UnsupportedEncodingException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail(), oAuth2User.getRole());
        String refreshToken = jwtService.createRefreshToken(oAuth2User.getEmail());
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);

        return String.format("?accessToken=%s&refreshToken=%s",
                URLEncoder.encode(accessToken, StandardCharsets.UTF_8),
                URLEncoder.encode(refreshToken, StandardCharsets.UTF_8));
    }


}
