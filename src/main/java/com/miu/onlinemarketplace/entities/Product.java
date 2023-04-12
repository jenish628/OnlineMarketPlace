package com.miu.onlinemarketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Transient
    private String images;
    private Boolean isVerified;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vendorId", referencedColumnName = "vendorId")
    private Vendor vendorId;


}
