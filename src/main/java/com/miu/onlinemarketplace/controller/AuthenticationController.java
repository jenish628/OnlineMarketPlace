package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.UserDTO;
import com.miu.onlinemarketplace.common.dto.VendorDTO;
import com.miu.onlinemarketplace.security.models.EnumRole;
import com.miu.onlinemarketplace.service.auth.AuthenticationService;
import com.miu.onlinemarketplace.service.auth.VendorService;
import com.miu.onlinemarketplace.service.auth.dtos.AuthResponseDTO;
import com.miu.onlinemarketplace.service.auth.dtos.LoginRequestDTO;
import com.miu.onlinemarketplace.service.auth.dtos.RegisterUserRequestDTO;
import com.miu.onlinemarketplace.service.auth.dtos.RegisterVendorRequestDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final VendorService vendorService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        log.info("Authentication API: login requested by user: ", loginRequest.getUsername());
        AuthResponseDTO authResponseDTO = authenticationService.loginUser(loginRequest);
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
        log.info("Authentication API: registerUser: ", registerUserRequestDTO.getEmail());
        UserDTO userDTO = authenticationService.createUser(registerUserRequestDTO, EnumRole.ROLE_USER);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/register-vendor")
    public ResponseEntity<?> registerVendor(@RequestBody RegisterVendorRequestDTO registerVendorRequestDTO) {
        log.info("Vendor API: registerVendor: ", registerVendorRequestDTO);
        UserDTO userDTO = authenticationService.createUser(registerVendorRequestDTO.getRegisterUser(), EnumRole.ROLE_VENDOR);
        VendorDTO vendorDTO = vendorService.createVendor(userDTO, registerVendorRequestDTO.getVendorDTO());
        return new ResponseEntity<>(vendorDTO, HttpStatus.OK);
    }


}