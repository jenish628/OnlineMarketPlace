package com.miu.onlinemarketplace.service.domain.shopping;

import com.miu.onlinemarketplace.common.dto.ShoppingCartDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ShoppingCartService {

    //get
    public List<ShoppingCartDTO> getCartItems();

    //post change qty from cart page
    boolean addQty(Long productId, Integer quantity);

    //put add product from product page
    boolean add(Long productId, Integer quantity);

    //remove Product
    boolean remove(Long productId);

}
