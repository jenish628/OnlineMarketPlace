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
    private Integer expYear;
    private Integer expMonth;
    private String cvc;
    private CardBrand cardBrand;
    private String addressType;
}
