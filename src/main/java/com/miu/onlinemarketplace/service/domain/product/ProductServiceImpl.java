package com.miu.onlinemarketplace.service.domain.product;

import com.miu.onlinemarketplace.common.dto.ProductDto;
import com.miu.onlinemarketplace.entities.Product;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> getAllProducts(Pageable pageable, Long categoryId) {
        Page<ProductDto> products;
        if(categoryId != null) {
            products = productRepository.findAllByProductCategory(pageable, categoryId)
                    .map(product -> modelMapper.map(product, ProductDto.class));
        } else {
            products = productRepository.findAll(pageable)
                    .map(product -> modelMapper.map(product, ProductDto.class));
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
        productRepository.save(product);
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
        product.setVendor(productDto.getVendor());
        product.setPrice(productDto.getPrice());
        product.setProductCategory(productDto.getProductCategory());
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public Boolean deleteProduct(ProductDto productDto ) {
        Product product = productRepository.findById(productDto.getProductId())
                .orElseThrow( () -> new DataNotFoundException("Product not found"));
        productRepository.deleteById(product.getProductId());
        return true;
    }
}
