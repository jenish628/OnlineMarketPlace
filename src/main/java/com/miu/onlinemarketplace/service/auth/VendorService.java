package com.miu.onlinemarketplace.service.auth;

import com.miu.onlinemarketplace.common.dto.UserDto;
import com.miu.onlinemarketplace.common.dto.VendorDto;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VendorService {

    Page<VendorDto> getAllVendors(Pageable pageable);

    VendorDto getVendorById(Long vendorId);

    VendorDto createVendor(UserDto userDTO, VendorDto vendorDTO);

    Page<VendorDto> filterVendorData(GenericFilterRequestDTO<VendorDto> genericFilterRequest, Pageable pageable);


}
