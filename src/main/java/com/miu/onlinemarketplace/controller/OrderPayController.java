package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.attest.Attest;
import com.miu.onlinemarketplace.common.dto.OrderPayDto;
import com.miu.onlinemarketplace.common.dto.OrderPayResponseDto;
import com.miu.onlinemarketplace.common.dto.ShoppingCartDto;
import com.miu.onlinemarketplace.common.dto.OrderPayInfoDto;
import com.miu.onlinemarketplace.service.orderPay.OrderPayService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/pay")
public class OrderPayController {

    private final OrderPayService orderPayService;

    public OrderPayController(OrderPayService orderPayService){
        this.orderPayService = orderPayService;
    }

    @PostMapping("/info")
    public ResponseEntity<OrderPayInfoDto> getOrderPayInfo(@Attest @RequestBody List<ShoppingCartDto> shoppingCartDtos) {
        return new ResponseEntity<>(orderPayService.findOrderPayInfo(shoppingCartDtos), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderPayResponseDto> createOrderPay(@Attest @RequestBody OrderPayDto orderPayDto){
        System.out.println("======orderPayDto====================================");
        return new ResponseEntity<>(orderPayService.createOrderPay(orderPayDto), HttpStatus.OK);
    }
}
