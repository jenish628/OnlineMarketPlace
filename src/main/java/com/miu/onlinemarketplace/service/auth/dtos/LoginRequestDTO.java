package com.miu.onlinemarketplace.service.auth.dtos;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String username;

    private String password;
}
