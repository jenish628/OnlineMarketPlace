package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.Product;
import com.miu.onlinemarketplace.entities.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product>findAllByName (String name);
    Page<Product> findAllByProductCategory(Long categoryId);
}
