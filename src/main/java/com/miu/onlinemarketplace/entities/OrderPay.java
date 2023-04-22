package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.CardBrand;
import com.miu.onlinemarketplace.common.enums.OrderPayStatus;
import com.miu.onlinemarketplace.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderPay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;

    private String cardNumber;
    private String nameOnCard;

    @Enumerated(value = EnumType.STRING)
    private CardBrand cardBrand;

    @Enumerated(value = EnumType.STRING)
    private OrderPayStatus orderPayStatus;

    private Long transactionId;

    @CreationTimestamp
    private LocalDateTime createdDate;

}
