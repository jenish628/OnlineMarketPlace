package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.enums.PaymentStatus;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private Long paymentId;
    private String cardNumber;
    private String cardHolderName;
    private Double payAmount;
    private String cardBrand;
    private Long transactionId;
    @Enumerated
    private PaymentStatus paymentStatus;
}
