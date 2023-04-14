package com.miu.onlinemarketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long vendorId;
    private String description;
    @ManyToOne
    private FileEntity logo;

    @OneToOne()
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @ManyToMany
    List<Payment> payments;
}
