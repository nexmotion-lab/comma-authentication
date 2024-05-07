package com.coders.commaauthentication.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "account")
@AllArgsConstructor
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id; // PK
    private String socialId; // ACCOUNT ID FROM RESOURCE SERVER
    private String nickname; // NAME FROM RESOURCE SERVER
    private String email;
    private LocalDate birthdate; // AGE FROM RESOURCE SERVER
    @Enumerated(EnumType.STRING)
    private Gender gender; // GENDER FROM RESOURCE SERVER
    @Enumerated(EnumType.STRING)
    private SocialType socialType; // OAUTH2 PROVIDER SUCH AS (KAKAO, NAVER, GOOGLE)
    @Enumerated(EnumType.STRING)
    private Role role; // PERMISSIONS BASED ON ROLES
    private String refreshToken; // REFRESH TOKEN FROM JWT
    @Transient
    private boolean isFirstLogin;

    public void setFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }


}
