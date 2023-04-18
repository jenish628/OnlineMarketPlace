package com.miu.onlinemarketplace.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private String paidBy;
    private LocalDateTime orderPlaced;
    private Long orderNumber;
    private double total;
    private LocalDateTime shippedDate;
    private List<OrderItemDto> orderItemList;
    private ShippingAddressDto shippingAddress;
    private List<PaymentDto> paymentMethod;
    @CreationTimestamp
    private LocalDateTime createdDate;
}
