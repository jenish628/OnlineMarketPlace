package com.miu.onlinemarketplace.service.domain.users;

import com.miu.onlinemarketplace.common.dto.UserDto;
import com.miu.onlinemarketplace.common.dto.VendorDto;
import com.miu.onlinemarketplace.entities.Role;
import com.miu.onlinemarketplace.entities.User;
import com.miu.onlinemarketplace.entities.Vendor;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.RoleRepository;
import com.miu.onlinemarketplace.repository.UserRepository;
import com.miu.onlinemarketplace.repository.VendorRepository;
import com.miu.onlinemarketplace.security.models.EnumRole;
import com.miu.onlinemarketplace.service.domain.users.dtos.VendorRegistrationRequest;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

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
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public VendorDto registerVendor(VendorRegistrationRequest vendorRegistrationRequest) {

        // Save as a user
        User user = new User();
        user.setEmail(vendorRegistrationRequest.getEmail());
        user.setFullName(vendorRegistrationRequest.getCompanyName());
        user.setPassword(passwordEncoder.encode(vendorRegistrationRequest.getPassword()));
        Role vendorRole = roleRepository.findOneByRole(EnumRole.ROLE_VENDOR);
        user.setRole(vendorRole);
        User returnedUser = userRepository.save(user);

        // Save to Vendor
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorRegistrationRequest.getCompanyName());
        vendor.setDescription(vendorRegistrationRequest.getDescription());
        vendor.setUser(returnedUser);
        vendorRepository.save(vendor);

        // TODO vendor payment, if failed, the whole transaction rolls back automatically
        // Payment - send CardInfo, amount, PaymentType
        // paymentService.vendorPayment(vendorRegistrationRequest.getCardInfo())

        VendorDto vendorDto = modelMapper.map(vendor, VendorDto.class);
        vendorDto.setUserDto(modelMapper.map(vendor.getUser(), UserDto.class));
        return vendorDto;
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
