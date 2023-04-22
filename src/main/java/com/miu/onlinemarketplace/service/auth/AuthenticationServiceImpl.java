package com.miu.onlinemarketplace.service.auth;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.miu.onlinemarketplace.common.dto.UserDto;
import com.miu.onlinemarketplace.entities.Role;
import com.miu.onlinemarketplace.entities.User;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.RoleRepository;
import com.miu.onlinemarketplace.repository.UserRepository;
import com.miu.onlinemarketplace.security.JwtTokenProvider;
import com.miu.onlinemarketplace.security.models.EnumRole;
import com.miu.onlinemarketplace.service.auth.dtos.AuthResponseDTO;
import com.miu.onlinemarketplace.service.auth.dtos.LoginRequestDTO;
import com.miu.onlinemarketplace.service.auth.dtos.RegisterUserRequestDTO;
import com.miu.onlinemarketplace.service.generic.dtos.GenericResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO loginUser(LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            String token = jwtTokenProvider.createToken(authentication);
            AuthResponseDTO authResponseDTO = new AuthResponseDTO();
            User user = userRepository.findByEmail(loginRequest.getUsername()).orElseThrow(() -> new DataNotFoundException("Error, while fetching user"));
            authResponseDTO.setUserId(user.getUserId());
            authResponseDTO.setFullName(user.getFullName());
            authResponseDTO.setUsername(user.getEmail());
            authResponseDTO.setEmail(user.getEmail());
            authResponseDTO.setRole(user.getRole().getRole().getValue());
            authResponseDTO.setToken(token);
            return authResponseDTO;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Bad Login Credentials");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDto createUser(RegisterUserRequestDTO registerUserRequestDTO, EnumRole enumRole) {
        User user = new User();
        user.setEmail(registerUserRequestDTO.getEmail());
        user.setFullName(registerUserRequestDTO.getFullName());
        user.setPassword(passwordEncoder.encode(registerUserRequestDTO.getPassword()));

        Role userRole = roleRepository.findOneByRole(enumRole);
        user.setRole(userRole);
        User returnedUser = userRepository.save(user);
        return mapUserToUserDTO(returnedUser);
    }

    @Override
    public GenericResponseDTO<Boolean> checkEmailAvailability(String email) {
        boolean exists = userRepository.existsByEmail(email);
        return new GenericResponseDTO<>(!exists);
    }


    private UserDto mapUserToUserDTO(User user) {
        UserDto userDTO = new UserDto();
        userDTO.setId(user.getUserId());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

}
