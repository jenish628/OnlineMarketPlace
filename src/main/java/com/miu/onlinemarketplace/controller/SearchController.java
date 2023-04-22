package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.entities.Product;
import com.miu.onlinemarketplace.service.domain.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("s")
public class SearchController {

    @Autowired
    SearchService searchService;
    @GetMapping
    public ResponseEntity<Page<Product>> search(
            @RequestParam(name="name", required = false) String name,
            @RequestParam(name="categoryName", required = false) String categoryName,
            @RequestParam(name="minPrice", required = false) Double minPrice,
            @RequestParam(name="maxPrice", required = false) Double maxPrice,
            @RequestParam(name="sortedPrice", required = false) String sortedPrice,
            Pageable pageable) {
        Page<Product> products = searchService.advanceSearch(name, categoryName, minPrice, maxPrice, sortedPrice, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
