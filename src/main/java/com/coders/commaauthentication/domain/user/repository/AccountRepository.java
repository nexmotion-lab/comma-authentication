package com.coders.commaauthentication.domain.user.repository;

import com.coders.commaauthentication.domain.user.Account;
import com.coders.commaauthentication.domain.user.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNickname(String name);

    Optional<Account> findById(Long id);

    Optional<Account> findBySocialId(String socialId);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByEmailAndRefreshToken(String email, String refreshToken);

    boolean existsByRefreshToken(String refreshToken);

    Optional<Account> findByEmailAndSocialType(String email, SocialType socialType);

    Optional<Account> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

}
