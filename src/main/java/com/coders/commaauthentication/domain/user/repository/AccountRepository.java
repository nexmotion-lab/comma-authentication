package com.coders.commaauthentication.domain.user.repository;

import com.coders.commaauthentication.domain.user.Account;
import com.coders.commaauthentication.domain.user.Gender;
import com.coders.commaauthentication.domain.user.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByEmailAndRefreshToken(String email, String refreshToken);

    @Query("SELECT a.id FROM Account a WHERE a.email = :email")
    Optional<Integer> findIdByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE Account a SET a.gender = :gender, a.birthdate = :birthdate, a.nickname = :nickname WHERE a.id = :id")
    void updateAccountInfo(Long id, Gender gender, LocalDate birthdate, String nickname);

    @Modifying
    @Query("UPDATE Account a SET a.nickname = :nickname WHERE a.id = :id")
    void updateAccountName(Long id, String nickname);



}
