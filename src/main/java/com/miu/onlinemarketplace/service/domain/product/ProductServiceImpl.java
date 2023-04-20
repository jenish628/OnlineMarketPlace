package com.miu.onlinemarketplace.service.domain.product;

import com.miu.onlinemarketplace.common.dto.ProductDto;
import com.miu.onlinemarketplace.common.dto.ProductResponseDto;
import com.miu.onlinemarketplace.entities.Product;

import com.miu.onlinemarketplace.entities.ProductCategory;
import com.miu.onlinemarketplace.entities.Vendor;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.ProductCategoryRepository;
import com.miu.onlinemarketplace.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public Page<ProductResponseDto> getAllProducts(Pageable pageable, Long categoryId) {
        Page<ProductResponseDto> products;
        if(categoryId != null) {
            products = productRepository.findAllByProductCategory(pageable, categoryId)
                    .map(product -> modelMapper.map(product, ProductResponseDto.class));
        } else {
            products = productRepository.findAll(pageable)
                    .map(product -> modelMapper.map(product, ProductResponseDto.class));
        }

        return products;
    }

    @Override
    public Page<ProductDto> getProductByName(Pageable pageable, String name) {
        Page<ProductDto> productDtos = productRepository.findAllByName(pageable, name)
                .map(product -> modelMapper.map(product, ProductDto.class));
        return productDtos;
    }

    @Override
    public ProductDto getByProductId(Long id) {
        ProductDto productDto = productRepository.findById(id)
                .map(product -> modelMapper.map(product, ProductDto.class)).get();
        return productDto;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct( ProductDto productDto) {
        Product product = productRepository.findById(productDto.getProductId())
                .orElseThrow( () -> new DataNotFoundException("Product not found"));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setIsVerified(productDto.getIsVerified());
        product.setIsDeleted(productDto.getIsDeleted());
        ProductCategory productCategory = productCategoryRepository.findById(productDto.getCategoryId()).orElseThrow(()->{
            log.error("Product category with id {} not found!!",productDto.getCategoryId());
            throw new DataNotFoundException("Product category with id "+productDto.getCategoryId()+" not found!!");
        });
        product.setProductCategory(productCategory);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public Boolean deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow( () -> new DataNotFoundException("Product not found"));
        productRepository.deleteById(product.getProductId());
        return true;
    }
}
