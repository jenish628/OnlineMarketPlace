package com.miu.onlinemarketplace.service.auth;

import com.miu.onlinemarketplace.common.dto.UserDto;
import com.miu.onlinemarketplace.security.models.EnumRole;
import com.miu.onlinemarketplace.service.auth.dtos.AuthResponseDTO;
import com.miu.onlinemarketplace.service.auth.dtos.LoginRequestDTO;
import com.miu.onlinemarketplace.service.auth.dtos.RegisterUserRequestDTO;
import com.miu.onlinemarketplace.service.generic.dtos.GenericResponseDTO;

public interface AuthenticationService {

    AuthResponseDTO loginUser(LoginRequestDTO loginRequest);

    UserDto createUser(RegisterUserRequestDTO registerUserRequestDTO, EnumRole enumRole);

    GenericResponseDTO<Boolean> checkEmailAvailability(String email);

}
