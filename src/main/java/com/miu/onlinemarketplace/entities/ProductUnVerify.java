package com.miu.onlinemarketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUnVerify {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long productId;
    private String name;
    private String description;
    private Integer quantity;
    @CreationTimestamp
    private LocalDateTime createdDate;

    private Boolean isVerified;
    private Boolean isDeleted;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vendorId", referencedColumnName = "vendorId")
    private Vendor vendor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private ProductCategory productCategory;

    @OneToOne
    @JoinColumn(name = "productId")
    private Product product;
}
