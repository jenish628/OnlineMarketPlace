package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.OrderDto;
import com.miu.onlinemarketplace.service.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OrderDto orderDto) {
        if (orderDto != null)
            return new ResponseEntity<>(orderService.saveOrder(orderDto), HttpStatus.OK);
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getById(@PathVariable Long orderId) {
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getOrderItemList(@RequestParam String productCode) {
        return new ResponseEntity<>(orderService.getAllOrderItemsByOrderCode(productCode), HttpStatus.OK);
    }
}
