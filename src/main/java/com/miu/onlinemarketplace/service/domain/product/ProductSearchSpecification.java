package com.miu.onlinemarketplace.service.domain.product;

import com.miu.onlinemarketplace.common.dto.ProductDto;
import com.miu.onlinemarketplace.entities.Product;
import com.miu.onlinemarketplace.entities.ProductCategory;
import com.miu.onlinemarketplace.entities.Vendor;
import com.miu.onlinemarketplace.service.generic.GenericSpecification;
import com.miu.onlinemarketplace.service.generic.SearchCriteria;
import com.miu.onlinemarketplace.service.generic.SearchOperation;
import com.miu.onlinemarketplace.service.generic.dtos.GenericFilterRequestDTO;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class ProductSearchSpecification {

    public static GenericSpecification<Product> processDynamicProductFilter(GenericFilterRequestDTO<ProductDto> genericFilterRequest) {
        GenericSpecification<Product> dynamicUserSpec = new GenericSpecification<Product>();
        ProductDto productFilter = genericFilterRequest.getDataFilter();
        if (productFilter.getProductId() != null) {
            dynamicUserSpec.add(new SearchCriteria("id", productFilter.getProductId(), SearchOperation.EQUAL));
        }
        if (productFilter.getName() != null) {
            dynamicUserSpec.add(new SearchCriteria("name", productFilter.getName(), SearchOperation.MATCH));
        }
        if (productFilter.getDescription() != null) {
            dynamicUserSpec.add(new SearchCriteria("role", productFilter.getDescription(), SearchOperation.MATCH));
        }
        return dynamicUserSpec;
    }

    public static Specification<Product> getProductByVendor(GenericFilterRequestDTO<ProductDto> genericFilterRequest) {
        Specification<Product> vendorEntitySpecification = new GenericSpecification<>();
        Long vendorId = Optional.ofNullable(genericFilterRequest)
                .map(GenericFilterRequestDTO<ProductDto>::getDataFilter)
                .map(ProductDto::getVendorId)
//                .map(VendorDto::getVendorId)
                .orElse(null);
        if (vendorId != null) {
            vendorEntitySpecification = (root, query, criteriaBuilder) -> {
                Join<Product, Vendor> vendorJoin = root.join("vendor");
                Predicate equalPredicate = criteriaBuilder.equal(vendorJoin.get("vendorId"), vendorId);
                query.distinct(true);
                return equalPredicate;
            };
        }
        return vendorEntitySpecification;
    }

    public static Specification<Product> getProductByCategory(GenericFilterRequestDTO<ProductDto> genericFilterRequest) {
        Specification<Product> categoryEntitySpecification = new GenericSpecification<>();
        Long categoryId = Optional.ofNullable(genericFilterRequest)
                .map(GenericFilterRequestDTO<ProductDto>::getDataFilter)
                .map(ProductDto::getCategoryId)
//                .map(ProductCategoryDto::getCategoryId)
                .orElse(null);
        if (categoryId != null) {
            categoryEntitySpecification = (root, query, criteriaBuilder) -> {
                Join<Product, ProductCategory> vendorJoin = root.join("category");
                Predicate equalPredicate = criteriaBuilder.equal(vendorJoin.get("categoryId"), categoryId);
                query.distinct(true);
                return equalPredicate;
            };
        }
        return categoryEntitySpecification;
    }

}
