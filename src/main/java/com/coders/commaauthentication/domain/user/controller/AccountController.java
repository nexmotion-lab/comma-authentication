package com.coders.commaauthentication.domain.user.controller;


import com.coders.commaauthentication.domain.user.Account;
import com.coders.commaauthentication.domain.user.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.ec.ECMultiplier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping("/findByEmail")
    public ResponseEntity<String> findAccountIdByEmail(@RequestParam String email) {

        return accountRepository.findIdByEmail(email)
                .map(id -> ResponseEntity.ok(id.toString()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
