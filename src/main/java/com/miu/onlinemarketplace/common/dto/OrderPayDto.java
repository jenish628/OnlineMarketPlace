package com.miu.onlinemarketplace.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderPayDto {
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    private Integer cardNumber;
    private String nameOnCard;
    private Integer securityCode;
    private Integer expiryMonth;
    private Integer expiryYear;
    private String cardBrand;

    private Long userId;
    private Integer quantity;
    private Double price;
    private String fullName;
}