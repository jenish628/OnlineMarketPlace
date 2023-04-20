package com.miu.onlinemarketplace.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDto {

    private Long cartId;
    private Integer quantity;

    private UserDto user;

    private ProductResponseDto product;
}
