package com.miu.onlinemarketplace.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SignUpMailSenderDto {
    private String toEmail;
    private LocalDateTime signupDateTime;
    private String verificationCode;
}
