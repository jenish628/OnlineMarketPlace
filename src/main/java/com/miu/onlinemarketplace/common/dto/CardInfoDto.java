package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.enums.AddressType;
import com.miu.onlinemarketplace.common.enums.CardBrand;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardInfoDto {

    private Long cardInfoId;
    private String cardNumber;
    private int lastFourDigits;
    private String nameOnCard;
    private Integer expYear;
    private Integer expMonth;
    private String cvc;
    private String cardBrand;
//    private String addressType;


}
