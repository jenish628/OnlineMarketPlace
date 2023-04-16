package com.miu.onlinemarketplace.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailHistoryDto {
    private String mailType;
    private String message;
    private String subject;
    private String fromEmail;
    private String toEmail;
}
