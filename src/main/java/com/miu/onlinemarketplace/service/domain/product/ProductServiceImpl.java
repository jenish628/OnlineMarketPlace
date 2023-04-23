package com.miu.onlinemarketplace.service.domain.product;

import com.lowagie.text.pdf.codec.wmf.MetaDo;
import com.miu.onlinemarketplace.common.dto.ProductDto;
import com.miu.onlinemarketplace.common.dto.ProductResponseDto;
import com.miu.onlinemarketplace.entities.Product;
import com.miu.onlinemarketplace.entities.ProductCategory;
import com.miu.onlinemarketplace.entities.ProductTemp;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.ProductCategoryRepository;
import com.miu.onlinemarketplace.repository.ProductRepository;
import com.miu.onlinemarketplace.repository.ProductTempRepository;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final ProductTempRepository productTempRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, ProductTempRepository productTempRepository, ProductCategoryRepository productCategoryRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.productTempRepository = productTempRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public Page<ProductResponseDto> getAllProducts(Pageable pageable, Long categoryId) {
        Page<ProductResponseDto> products;
        if (categoryId != null) {
            products = productRepository.findAllByProductCategory(pageable, categoryId)
                    .map(product -> modelMapper.map(product, ProductResponseDto.class));
        } else {
            products = productRepository.findAll(pageable)
                    .map(product -> modelMapper.map(product, ProductResponseDto.class));
        }
        return products;
    }
    @Override
    public Page<ProductResponseDto> getAllProductsOfVendor(Pageable pageable, Long vendorId) {
        Page<ProductResponseDto> products;
        Page<Product> product = productRepository.findAllByVendor(pageable, vendorId);
        products = productRepository.findAll(pageable)
                .map(p -> modelMapper.map(product, ProductResponseDto.class));
        return products;
    }

    @Override
    public Page<ProductResponseDto> getCustomerProducts(Pageable pageable, Long categoryId) {
        Page<ProductResponseDto> products;
        if (categoryId != null) {
            products = productRepository.findByIsDeletedAndIsVerified(pageable, true, false)
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
    public ProductResponseDto getProductByProductId(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow();
        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto getByProductId(Long id) {
        ProductResponseDto productDto = productRepository.findById(id)
                .map(product -> modelMapper.map(product, ProductResponseDto.class)).orElseThrow(() -> {
                    log.error("Product category with id {} not found!!");
                    throw new DataNotFoundException("Product category with id  not found!!");
                });
        ;
        return productDto;
    }

    @Override
    public ProductDto createNewProduct(ProductDto productDto) {
        ProductTemp productTemp = modelMapper.map(productDto, ProductTemp.class);
        productTemp = productTempRepository.save(productTemp);
        return modelMapper.map(productTemp, ProductDto.class);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ProductResponseDto verifyProduct(Long productId, boolean isVerified) {
        Product product = productRepository.findById(productId).get();
        product.setIsVerified(isVerified);
        product = productRepository.save(product);
        productTempRepository.deleteById(productId);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setIsVerified(false);
        product.setIsDeleted(false);
        ProductCategory productCategory = productCategoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> {
            log.error("Product category with id {} not found!!", productDto.getCategoryId());
            throw new DataNotFoundException("Product category with id " + productDto.getCategoryId() + " not found!!");
        });
        product.setProductCategory(productCategory);

        ProductTemp productTemp = modelMapper.map(product, ProductTemp.class);
        return modelMapper.map(productTempRepository.save(productTemp), ProductDto.class);
    }

    @Override
    public Boolean deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        product.setIsDeleted(true);
        productRepository.save(product);
        return true;
    }

    @Override
    public Page<ProductDto> filterProductData(GenericFilterRequestDTO<ProductDto> genericFilterRequest, Pageable pageable) {
        Specification<Product> specification = Specification
                .where(ProductSearchSpecification.processDynamicProductFilter(genericFilterRequest))
                .and(Specification.where(ProductSearchSpecification.getProductByVendor(genericFilterRequest)))
                .and(Specification.where(ProductSearchSpecification.getProductByCategory(genericFilterRequest)));
        Page<ProductDto> filteredProducts = productRepository.findAll(specification, pageable).map(product ->
                modelMapper.map(product, ProductDto.class));
        return filteredProducts;
    }
}
