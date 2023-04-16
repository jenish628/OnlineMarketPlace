package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.ShoppingCartDTO;
import com.miu.onlinemarketplace.entities.Product;
import com.miu.onlinemarketplace.service.domain.shopping.ShoppingCartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/shopping-cart")
//@AllArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }
    //get
    public ResponseEntity<?> getShoppingCartItems(){
        Map<String, Object> res = Map.of("res", shoppingCartService.getCartItems());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    // post
    @PostMapping()
    public ResponseEntity<?> addProductQtyToCart(@RequestParam Long productId, @RequestParam Integer quantity){
        Map<String, Object> res = Map.of("res", shoppingCartService.addQty(productId, quantity));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    //put
    @PutMapping()
    public ResponseEntity<?> addProductToCart(@RequestParam Long productId, @RequestParam Integer quantity){
        Map<String, Object> res = Map.of("res", shoppingCartService.add(productId,quantity));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }



    //delete product from shopping cart
    @DeleteMapping()
    public ResponseEntity<?> removeProductFromCart(@RequestParam Long productId){
        Map<String, Object> res = Map.of("res", shoppingCartService.remove(productId));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
