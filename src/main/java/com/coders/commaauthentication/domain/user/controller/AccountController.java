package com.coders.commaauthentication.domain.user.controller;


import com.coders.commaauthentication.domain.jwt.model.TokenResponse;
import com.coders.commaauthentication.domain.user.Account;
import com.coders.commaauthentication.domain.user.Gender;
import com.coders.commaauthentication.domain.user.controller.service.AccountService;
import com.coders.commaauthentication.domain.user.dto.AccountInfoDTO;
import com.coders.commaauthentication.domain.user.repository.AccountRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;

@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/token/resend")
    public ResponseEntity<TokenResponse> resendToken(HttpServletRequest request) {
        String accessToken = null;
        String refreshToken = null;

        if (request.getCookies() != null) {
            accessToken = Arrays.stream(request.getCookies())
                    .filter(cookie -> "accessToken".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);

            refreshToken = Arrays.stream(request.getCookies())
                    .filter(cookie -> "refreshToken".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        // TokenResponse 객체 생성
        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);

        // 응답 반환
        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<String> findAccountIdByEmail(@RequestParam String email) {
        return ResponseEntity.ok(accountService.findIdByEmail(email).toString());
    }

    @PostMapping("/info")
    public ResponseEntity<Void> updateAccountInfo(@RequestParam Gender gender,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthdate,
                                                  @RequestParam @Size(max = 12, message = "닉네임은 최대 12글자 입니다.") String nickname,
                                                  HttpServletRequest request) {
        accountService.updateAccountInfo(Long.parseLong(request.getHeader("X-User-Id")), gender, birthdate, nickname);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/name")
    public ResponseEntity<String> updateAccountName(@RequestParam @Size(max = 12, message = "닉네임은 최대 12글자 입니다.") String nickname,
                                                    HttpServletRequest request) {

        String name = accountService.updateAccountName(Long.parseLong(request.getHeader("X-User-Id")), nickname);
        return ResponseEntity.ok(name);
    }

    @GetMapping("/info")
    public ResponseEntity<AccountInfoDTO> getAccountInfo(HttpServletRequest request) {
        AccountInfoDTO accountInfo = accountService.getAccountInfo(Long.parseLong(request.getHeader("X-User-Id")));
        return ResponseEntity.ok(accountInfo);
    }

}
