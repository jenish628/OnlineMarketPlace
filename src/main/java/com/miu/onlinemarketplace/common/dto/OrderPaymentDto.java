package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.dto.AddressDto;
import com.miu.onlinemarketplace.common.dto.CardInfoDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderPaymentDto {

    private Long userId;
    private AddressDto shippingAddressDto;
    private CardInfoDto cardInfoDto;
    private Integer quantity;
    private BigDecimal totalPrice;

}
