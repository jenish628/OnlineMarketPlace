package com.miu.onlinemarketplace.service.auth.dtos;

import com.miu.onlinemarketplace.common.dto.VendorDTO;
import lombok.Data;

@Data
public class RegisterVendorRequestDTO {

    private RegisterUserRequestDTO registerUser;

    private VendorDTO vendorDTO;

    // other details

}