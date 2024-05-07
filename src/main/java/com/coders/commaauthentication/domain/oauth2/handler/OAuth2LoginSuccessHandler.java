package com.coders.commaauthentication.domain.oauth2.handler;

import com.coders.commaauthentication.domain.jwt.service.JwtService;
import com.coders.commaauthentication.domain.oauth2.CustomOAuth2User;
import com.coders.commaauthentication.domain.user.Role;
import com.coders.commaauthentication.domain.user.repository.AccountRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final AccountRepository accountRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        loginSuccess(response, oAuth2User);
        String redirectURL = getRedirectURL(request);
        if (redirectURL != null) {
            response.sendRedirect(redirectURL);
        }
            response.sendRedirect("http://gateway/home");
    }


    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail(), oAuth2User.getRole());
        String refreshToken = jwtService.createRefreshToken(oAuth2User.getEmail());

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
        log.info("토큰발급");
    }

    private String getRedirectURL(HttpServletRequest request) {
        String[] redirectURLs = request.getParameterValues("redirectURL");
        if (redirectURLs != null && redirectURLs.length > 0) {
            return redirectURLs[0];
        }

        return null;
    }





}
