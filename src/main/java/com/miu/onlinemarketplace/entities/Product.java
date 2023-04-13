package com.miu.onlinemarketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    private String name;
    private String description;
    private Integer quantity;

//    @ManyToMany
//    List<FileEntity> images;

    private Boolean isVerified;
    private Boolean isDeleted;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vendorId", referencedColumnName = "vendorId")
    private Vendor vendor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private ProductCategory productCategory;

}
