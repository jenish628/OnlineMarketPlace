package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByProduct_ProductIdAndUser_UserId(Long productId,Long userId);
    List<ShoppingCart> findAllByUser_UserId(Long userId);

}
