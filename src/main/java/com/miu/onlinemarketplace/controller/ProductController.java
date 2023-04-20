package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.ProductDto;
import com.miu.onlinemarketplace.common.dto.ProductResponseDto;
import com.miu.onlinemarketplace.service.domain.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(@PageableDefault(page = 0, size = 10, sort = "productId",
            direction = Sort.Direction.DESC) Pageable pageable,
                                          @RequestParam(required = false) Long categoryId
                                            ){
        Page page = productService.getAllProducts(pageable, categoryId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/products/name/{name}")
    public ResponseEntity<?> getProductByName(
            @PageableDefault(page = 0, size = 10, sort = "productId",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable String name){
        Page page = productService.getProductByName(pageable, name);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getByProductId(@PathVariable Long productId){
        ProductResponseDto productDto = productService.getByProductId(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto){
        ProductDto productDto1 = productService.createProduct(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto){
        ProductDto productDto1 = productService.updateProduct(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.OK);
    }

    @DeleteMapping("/products")
    public ResponseEntity<?> deleteProduct(@RequestParam Long productId){
        Boolean product = productService.deleteProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
