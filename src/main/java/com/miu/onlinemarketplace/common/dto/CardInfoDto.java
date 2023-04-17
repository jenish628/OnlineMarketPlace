package com.miu.onlinemarketplace.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardInfoDto {

    private Integer cardNumber;
    private String nameOnCard;
    private Integer expiryMonth;
    private Integer expiryYear;
    private Integer securityCode;
    private String cardBrand;
}
