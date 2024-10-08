package com.coders.commaauthentication.domain.user.controller.service;

import com.coders.commaauthentication.domain.user.Account;
import com.coders.commaauthentication.domain.user.Gender;
import com.coders.commaauthentication.domain.user.dto.AccountInfoDTO;
import com.coders.commaauthentication.domain.user.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.http.HttpRequest;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Integer findIdByEmail(String email) {
        return accountRepository.findIdByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(email + " 에 해당하는 계정 정보가 존재하지 않습니다.")
        );
    }

    @Transactional
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Transactional
    public void updateAccountInfo(Long id, Gender gender, LocalDate birthdate, String nickname) {
        accountRepository.updateAccountInfo(id, gender, birthdate, nickname);
    }

    @Transactional
    public String updateAccountName(Long id, String nickname) {
        accountRepository.updateAccountName(id, nickname);
        return nickname;
    }

    public AccountInfoDTO getAccountInfo(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하는 계정 정보가 없습니다."));
        return new AccountInfoDTO(account.getNickname(), account.getGender(), account.getBirthdate());

    }
}
