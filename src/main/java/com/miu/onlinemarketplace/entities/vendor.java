package com.miu.onlinemarketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vendorId;
    private String description;
    @Transient
    private String logo;
}
