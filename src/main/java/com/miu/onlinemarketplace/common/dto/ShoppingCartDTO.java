package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.entities.Product;
import com.miu.onlinemarketplace.entities.User;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ShoppingCartDTO {

    private Long cartId;
    private Integer quantity;

//    @ManyToOne
    private User user;

//    @ManyToOne
    private Product product;
}
