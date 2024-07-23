package com.coders.commaauthentication.domain.user.controller;


import com.coders.commaauthentication.domain.user.Account;
import com.coders.commaauthentication.domain.user.Gender;
import com.coders.commaauthentication.domain.user.controller.service.AccountService;
import com.coders.commaauthentication.domain.user.repository.AccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.ec.ECMultiplier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

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
}
