package com.miu.onlinemarketplace.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    private Long id;
    private String address1;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
