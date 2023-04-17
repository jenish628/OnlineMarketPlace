package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.AddressType;
import com.miu.onlinemarketplace.common.enums.CardBrand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cardNumber;
    private Integer securityCode;
    private Integer expiryMonth;
    private Integer expiryYear;
    @Enumerated
    private CardBrand cardBrand;
    @Enumerated
    private AddressType addressType;

    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;


}
