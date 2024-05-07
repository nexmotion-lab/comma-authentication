package com.coders.commaauthentication.domain.jwt.controller;

import com.coders.commaauthentication.domain.jwt.service.JwtService;
import com.coders.commaauthentication.domain.user.Role;
import com.coders.commaauthentication.domain.user.repository.AccountRepository;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jwt")
@AllArgsConstructor
public class JwtController {

    private final JwtService jwtService;
    private final AccountRepository accountRepository;

    @PostMapping("/createRefreshToken/{email}")
    public Map<String, String> createRefreshToken(@PathVariable String email) {
        Map<String, String> jwtToken = new HashMap<>();
        jwtToken.put(JwtService.ACCESS_TOKEN_SUBJECT, jwtService.createRefreshToken(email));
        return jwtToken;
    }

    @PostMapping("/createAccessAndRefreshToken/{email}{role}")
    public Map<String, String> createRefreshAndAccessToken(@PathVariable String email, @PathVariable Role role) {
        Map<String, String> jwtToken = new HashMap<>();
        jwtToken.put(JwtService.ACCESS_TOKEN_SUBJECT, jwtService.createRefreshToken(email));
        jwtToken.put(JwtService.REFRESH_TOKEN_SUBJECT, jwtService.createAccessToken(email, role));
        return jwtToken;
    }

    @PostMapping("/createAccessAndRefreshToken/{email}")
    public Map<String, String> createRefreshAndAccessToken(@PathVariable String email) {
        Map<String, String> jwtToken = accountRepository.findByEmail(email)
                .map(account -> {
                    Map<String, String> tokens = new HashMap<>();
                    tokens.put(JwtService.ACCESS_TOKEN_SUBJECT, jwtService.createAccessToken(email, account.getRole()));
                    tokens.put(JwtService.REFRESH_TOKEN_SUBJECT, jwtService.createRefreshToken(email));
                    return tokens;
                })
                .orElseThrow(() -> new RuntimeException("Account not found for email: " + email));
        return jwtToken;
    }

}
