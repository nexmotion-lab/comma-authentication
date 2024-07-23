package com.coders.commaauthentication.domain.user.dto;

import com.coders.commaauthentication.domain.user.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class AccountInfoDTO {

    public String nickname;
    public Gender gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate birthdate;
}
