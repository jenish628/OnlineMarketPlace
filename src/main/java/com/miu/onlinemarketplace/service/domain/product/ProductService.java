package com.miu.onlinemarketplace.service.domain.product;

import com.miu.onlinemarketplace.common.dto.ProductDto;
import com.miu.onlinemarketplace.common.dto.ProductResponseDto;
import com.miu.onlinemarketplace.entities.ProductTemp;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductService {

    Page<ProductResponseDto> getCustomerProducts(Pageable pageable, Long categoryId);
    Page<ProductResponseDto> getAllProducts(Pageable pageable, Long categoryId);
    Page <ProductDto> getProductByName(Pageable pageable, String name);
    ProductResponseDto getProductByProductId(Long id);
    ProductResponseDto getByProductId(Long id);
    ProductDto createNewProduct(ProductDto productDto);
    ProductResponseDto verifyProduct(Long productId);
    ProductDto updateProduct(ProductDto productDto );
    Boolean deleteProduct(Long productId) ;

    Page<ProductDto> filterProductData(GenericFilterRequestDTO<ProductDto> genericFilterRequest, Pageable pageable);

}
