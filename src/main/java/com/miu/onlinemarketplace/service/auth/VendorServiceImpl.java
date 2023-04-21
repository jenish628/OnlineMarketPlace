package com.miu.onlinemarketplace.service.auth;

import com.miu.onlinemarketplace.common.dto.UserDto;
import com.miu.onlinemarketplace.common.dto.VendorDto;
import com.miu.onlinemarketplace.entities.User;
import com.miu.onlinemarketplace.entities.Vendor;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.UserRepository;
import com.miu.onlinemarketplace.repository.VendorRepository;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public Page<VendorDto> getAllVendors(Pageable pageable) {
        return vendorRepository.findAll(pageable)
                .map(vendor -> modelMapper.map(vendor, VendorDto.class));
    }

    @Override
    public VendorDto getVendorById(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new DataNotFoundException("Vendor not found"));
        VendorDto vendorDto = modelMapper.map(vendor, VendorDto.class);
        return vendorDto;
    }

    @Override
    public VendorDto createVendor(UserDto userDTO, VendorDto vendorDTO) {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException());
        // write implementation to save Vendor
        // - map to entity
        // - set user
        return vendorDTO;
    }

    @Override
    public Page<VendorDto> filterVendorData(GenericFilterRequestDTO<VendorDto> genericFilterRequest, Pageable pageable) {
        return null;
    }

    @Override
    public VendorDto verifyVendor(VendorDto vendorDto) {
        Vendor vendor = modelMapper.map(vendorDto, Vendor.class);
        Vendor updatedVendor = vendorRepository.save(vendor);
        return modelMapper.map(updatedVendor, VendorDto.class);
    }


}
