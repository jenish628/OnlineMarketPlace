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

    private Long cardInfoId;
    private String cardNumber;
    private Integer expYear;
    private Integer expMonth;
    private String cvc;
    @Enumerated
    private CardBrand cardBrand;
    @Enumerated
    private AddressType addressType;

    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;


}
