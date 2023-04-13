package com.miu.onlinemarketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;
    private String category;


}
