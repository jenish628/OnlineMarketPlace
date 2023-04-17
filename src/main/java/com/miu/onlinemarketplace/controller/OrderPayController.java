package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.OrderPayDto;
import com.miu.onlinemarketplace.common.dto.ResponseDto;
import com.miu.onlinemarketplace.service.payment.OrderPayService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/order/pay")
public class OrderPayController {

    private final OrderPayService orderPayService;

    @GetMapping("/test")
    public String test(){
        System.out.println("testing....");
        return "====== test";
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDto> createOrderPay(@RequestBody OrderPayDto orderPayDto){
        orderPayService.createOrderPay(orderPayDto);
        return null;
    }
}
