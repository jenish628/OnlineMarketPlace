package com.miu.onlinemarketplace.service.auth;

import com.miu.onlinemarketplace.common.dto.UserDto;
import com.miu.onlinemarketplace.common.dto.VendorDto;
import com.miu.onlinemarketplace.entities.User;
import com.miu.onlinemarketplace.repository.UserRepository;
import com.miu.onlinemarketplace.repository.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;

    @Override
    public VendorDto createVendor(UserDto userDTO, VendorDto vendorDTO) {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException());
        // write implementation to save Vendor
        // - map to entity
        // - set user
        return vendorDTO;
    }
}
