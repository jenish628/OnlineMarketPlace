package com.miu.onlinemarketplace.service.auth;

import com.miu.onlinemarketplace.common.dto.UserDTO;
import com.miu.onlinemarketplace.common.dto.VendorDTO;
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
    public VendorDTO createVendor(UserDTO userDTO, VendorDTO vendorDTO) {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException());
        // write implementation to save Vendor
        // - map to entity
        // - set user
        return vendorDTO;
    }
}
