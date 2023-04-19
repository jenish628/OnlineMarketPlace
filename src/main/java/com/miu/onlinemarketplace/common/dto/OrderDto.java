package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long OrderId;
    private OrderStatus orderStatus;
    private ShippingAddressDto shipping;
    private UserDto user;
    @CreationTimestamp
    private LocalDateTime orderDate;
    List<PaymentDto> payments;
}
