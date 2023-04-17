package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.entities.ProductCategory;
import com.miu.onlinemarketplace.entities.Vendor;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {

    private Long productId;
    private String name;
    private String description;
    private Integer quantity;

//    @ManyToMany
//    List<FileEntity> images;

    private Boolean isVerified;
    private Boolean isDeleted;

    private Vendor vendor;
    private ProductCategory productCategory;
}
