package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.ProductDto;
import com.miu.onlinemarketplace.common.dto.VendorDto;
import com.miu.onlinemarketplace.service.auth.VendorService;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllVendors(@PageableDefault(page = 0, size = 10, sort = "vendorId",
            direction = Sort.Direction.DESC) Pageable pageable) {
        Page<VendorDto> vendorPageable = vendorService.getAllVendors(pageable);
        return new ResponseEntity<>(vendorPageable, HttpStatus.OK);
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<?> getByVendorId(@PathVariable Long id) {
        VendorDto vendorDto = vendorService.getVendorById(id);
        return new ResponseEntity<>(vendorDto, HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filterVendorData(@RequestBody GenericFilterRequestDTO<VendorDto> genericFilterRequest, Pageable pageable) {
        log.info("Vendor API: Filter vendor data");
        Page<VendorDto> vendorPageable = vendorService.filterVendorData(genericFilterRequest, pageable);
        return new ResponseEntity<>(vendorPageable, HttpStatus.OK);
    }
}
