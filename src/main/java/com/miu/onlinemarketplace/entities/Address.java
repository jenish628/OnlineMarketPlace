package com.miu.onlinemarketplace.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long addressId;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String country;

//    @Enumerated
//    private AddressType addressType;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "userId", referencedColumnName = "userId")
//    private User userId;
}
