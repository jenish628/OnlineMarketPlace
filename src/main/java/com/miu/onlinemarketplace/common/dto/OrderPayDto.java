package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.enums.CardBrand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPayDto {
    private Long userId;
    private String fullName;

    private double price;
    private Integer quantity;

    private AddressDto addressDto;
    private CardInfoDto cardInfoDto;

    private String cardNumber;
    private String nameOnCard;
    private int expMonth;
    private int expYear;
    private String CVC;
    private Double amount;
//    private CardBrand cardBrand;

    List<ShoppingCartDto> shoppingCartDtos;
    private boolean isGuestUser;
//    private Long transactionId;
}
