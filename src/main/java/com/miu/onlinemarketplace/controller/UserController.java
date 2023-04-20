package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.ResponseDto;
import com.miu.onlinemarketplace.common.dto.UserCardInfoDto;
import com.miu.onlinemarketplace.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserCardInfoDto> findUserById(@PathVariable Long id){
        UserCardInfoDto userCardInfoDto = userService.findInfoById(id);
        return new ResponseEntity<>(userCardInfoDto, HttpStatus.OK);
    }


}
