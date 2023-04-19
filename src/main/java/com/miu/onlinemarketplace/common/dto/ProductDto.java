package com.miu.onlinemarketplace.common.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {

    private Long productId;
    private String name;
    private String description;
    private Integer quantity;
    @CreationTimestamp
    private LocalDateTime createdDate;

    private Boolean isVerified;
    private Boolean isDeleted;

    private VendorDto vendor;
    private ProductCategoryDto productCategory;
}
