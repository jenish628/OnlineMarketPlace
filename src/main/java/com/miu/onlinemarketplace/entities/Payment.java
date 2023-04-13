package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long paymentId;
    private String cardNumber;
    private String cardHolderName;
    private Double payAmount;
    private String cardBrand;
    private Long transactionId;
    @Enumerated
    private PaymentStatus paymentStatus;
}
