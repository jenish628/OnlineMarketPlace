package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.ProductDto;
import com.miu.onlinemarketplace.common.dto.ProductResponseDto;
import com.miu.onlinemarketplace.entities.Product;
import com.miu.onlinemarketplace.entities.ProductTemp;
import com.miu.onlinemarketplace.service.domain.product.ProductService;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VENDOR')")
    @GetMapping("/allProducts")
    public ResponseEntity<?> getAllProducts(@PageableDefault(page = 0, size = 10, sort = "productId",
            direction = Sort.Direction.DESC) Pageable pageable,
                                            @RequestParam(required = false) Long categoryId
    ) {
        // TODO - if ROLE_VENDOR, dont show UNVERIFIED product from other-vendor
        Page<ProductResponseDto> page = productService.getAllProducts(pageable, categoryId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    // TODO Public - Get All Products for Customer
    @GetMapping("/products")
    public ResponseEntity<?> getCustomerProducts(@PageableDefault(page = 0, size = 10, sort = "productId",
            direction = Sort.Direction.DESC) Pageable pageable,
                                                 @RequestParam(required = false) Long categoryId
    ) {
        Page page = productService.getCustomerProducts(pageable, categoryId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    // TODO Public - Get All Products form Name
    @GetMapping("/products/name/{name}")
    public ResponseEntity<?> getProductByName(
            @PageableDefault(page = 0, size = 10, sort = "productId",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable String name) {
        Page page = productService.getProductByName(pageable, name);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    // TODO public -
    @GetMapping("/allProducts/{productId}")
    public ResponseEntity<?> getAllProductId(@PathVariable Long productId) {
        ProductResponseDto productDto = productService.getProductByProductId(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    // Get TODO Products for Customer
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getByProductId(@PathVariable Long productId) {
        ProductResponseDto productDto = productService.getByProductId(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_VENDOR')")
    @PostMapping("/products")
    public ResponseEntity<?> createNewProduct(@RequestBody ProductDto productDto) {
        ProductDto productDto1 = productService.createNewProduct(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/products/verify")
    public ResponseEntity<?> verifyProduct(@RequestParam Long productId) {
        ProductResponseDto productDto = productService.verifyProduct(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_VENDOR')")
    @PutMapping("/products")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto) {
        ProductDto productDto1 = productService.updateProduct(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_VENDOR')")
    @DeleteMapping("/products")
    public ResponseEntity<?> deleteProduct(@RequestParam Long productId) {
        Boolean product = productService.deleteProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VENDOR')")
    @PostMapping("/filter")
    public ResponseEntity<?> filterProductData(@RequestBody GenericFilterRequestDTO<ProductDto> genericFilterRequest, Pageable pageable) {
        log.info("Product API: Filter user data");
        Page<ProductDto> productPageable = productService.filterProductData(genericFilterRequest, pageable);
        return new ResponseEntity<>(productPageable, HttpStatus.OK);
    }

}
