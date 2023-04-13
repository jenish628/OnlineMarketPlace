package com.miu.onlinemarketplace.common.dto;

import lombok.Data;

@Data
public class VendorDTO {

    private Long vendorId;
    private String description;

    private UserDTO userDTO;

    //    private FileEntity logo;
    //    List<Payment> payments;
}
