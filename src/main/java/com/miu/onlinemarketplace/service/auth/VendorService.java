package com.miu.onlinemarketplace.service.auth;

import com.miu.onlinemarketplace.common.dto.UserDTO;
import com.miu.onlinemarketplace.common.dto.VendorDTO;

public interface VendorService {

    VendorDTO createVendor(UserDTO userDTO, VendorDTO vendorDTO);

}
