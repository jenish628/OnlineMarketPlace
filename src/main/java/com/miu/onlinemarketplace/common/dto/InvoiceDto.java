package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.entities.Address;
import com.miu.onlinemarketplace.entities.OrderItem;
import com.miu.onlinemarketplace.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<OrderItem> orderItemList;
    private Address shippingAddress;
    private List<Payment> paymentMethod;
}
