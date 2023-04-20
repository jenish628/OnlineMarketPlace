package com.miu.onlinemarketplace.common.dto;


import com.miu.onlinemarketplace.entities.ProductCategory;
import com.miu.onlinemarketplace.entities.Vendor;
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
    private Double price;

//    @ManyToMany
//    List<FileEntity> images;

@CreationTimestamp
    private LocalDateTime createdDate;
    private Boolean isVerified;
    private Boolean isDeleted;
    private VendorDto vendor;
    private ProductCategoryDto productCategory;
}
