package com.miu.onlinemarketplace.service.auth;

import com.miu.onlinemarketplace.common.dto.UserDto;
import com.miu.onlinemarketplace.common.dto.VendorDto;

public interface VendorService {

    VendorDto createVendor(UserDto userDTO, VendorDto vendorDTO);

}
