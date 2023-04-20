package com.miu.onlinemarketplace.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCardInfoDto {

    private Long userId;
    private String fullName;

    private AddressDto addressDto;

    private CardInfoDto cardInfoDto;

}
