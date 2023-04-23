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

    // Authenticated USer
    private AddressDto addressDto;
    private CardInfoDto cardInfoDto;

    private boolean isGuestUser;
    private String clientIp;
    private String cardId;
    private Long transactionId;

    private Long userId;
    private String fullName;
    private String email;
    private double price;
    private Integer quantity;

    // Guest User
    private String cardNumber;
    private String nameOnCard;
    private int expMonth;
    private int expYear;
    private String CVC;
    private Double amount;
    private CardBrand cardBrand;


    List<ShoppingCartDto> shoppingCartDtos;
//    private
}
