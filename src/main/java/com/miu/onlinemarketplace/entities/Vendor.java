package com.miu.onlinemarketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vendorId;
    private String description;
//    @ManyToOne
//    private FileEntity fileEntity;   (This is Vendor logo)

    @OneToOne()
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
}
