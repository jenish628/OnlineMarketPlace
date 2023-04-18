package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long OrderId;
    private OrderStatus orderStatus;
    private ShippingAddressDto shipping;
    private UserDTO userId;
    private LocalDateTime orderDate;
    List<PaymentDto> payments;
}
