package com.miu.onlinemarketplace.service.domain.product;

import com.miu.onlinemarketplace.common.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductService {

    Page<ProductDto> getAllProducts(Pageable pageable, Long categoryId);
    Page <ProductDto> getProductByName(Pageable pageable, String name);
    ProductDto getByProductId(Long id);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto );
    Boolean deleteProduct(Long productId) ;

}
