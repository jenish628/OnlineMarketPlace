package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.entities.OrderItem;
import com.miu.onlinemarketplace.service.accountcommission.AccountCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountCommissionController {

    @Autowired
    private AccountCommissionService accountCommissionService;

    @PostMapping("/accountcommission")
    public ResponseEntity<?> saveAccountCommission(@Validated @RequestBody List<OrderItem> orderItem) {
        if(accountCommissionService != null) {
            accountCommissionService.saveCommission();
            return new ResponseEntity<>("Account Commission Save Successfully!!!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Account Commission Save Successfully!!!", HttpStatus.BAD_REQUEST);
    }
}
