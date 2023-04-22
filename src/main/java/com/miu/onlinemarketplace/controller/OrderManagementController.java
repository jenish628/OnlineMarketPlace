package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.OrderResponseDto;
import com.miu.onlinemarketplace.service.ordermanage.OrderManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/manage-order")
public class OrderManagementController {

    private final OrderManagementService orderManagementService;


    public OrderManagementController(OrderManagementService orderManagementService) {
        this.orderManagementService = orderManagementService;
    }

    //get for user
    @GetMapping("/user")
    public ResponseEntity<?> getOrderUser(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<OrderResponseDto> orderResponseDtos = orderManagementService.getAllOrderOfCurrentUser(pageable);
        return new ResponseEntity<>(orderResponseDtos, HttpStatus.OK);
    }


    //get for vendor
    @GetMapping("/vendor")
    public ResponseEntity<?> getOrderVendor(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<OrderResponseDto> orderResponseDtos = orderManagementService.getAllOrderByVendor(pageable);
        return new ResponseEntity<>(orderResponseDtos, HttpStatus.OK);
    }


    //get for Admin
    @GetMapping("/admin")
    public ResponseEntity<?> getOrderAdmin(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<OrderResponseDto> orderResponseDtos = orderManagementService.getAllOrders(pageable);
        return new ResponseEntity<>(orderResponseDtos, HttpStatus.OK);
    }


}
