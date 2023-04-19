package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.ProductCategoryDto;
import com.miu.onlinemarketplace.service.domain.product.ProductCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/category")
    public ResponseEntity<?> GetAllCategory(){
        List<ProductCategoryDto> categories = productCategoryService.getAllCategory();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody ProductCategoryDto productCategory){
        ProductCategoryDto newCategory = productCategoryService.createCategory(productCategory);
        return new ResponseEntity<>(newCategory, HttpStatus.OK);
    }
    @PutMapping("/category")
    public ResponseEntity<?> updateCategory(@RequestBody ProductCategoryDto productCategory){
        ProductCategoryDto newCategory = productCategoryService.updateCategory(productCategory);
        return new ResponseEntity<>(newCategory, HttpStatus.OK);
    }
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable ("categoryId") Long categoryId){
        ProductCategoryDto newCategory = productCategoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(newCategory, HttpStatus.OK);
    }
}
