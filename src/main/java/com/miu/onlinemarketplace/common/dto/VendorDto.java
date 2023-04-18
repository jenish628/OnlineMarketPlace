package com.miu.onlinemarketplace.common.dto;

import lombok.Data;

@Data
public class VendorDto {

    private Long vendorId;
    private String vendorName;
    private String description;

    private UserDto userDto;
}
