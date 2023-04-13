package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shoppingId;
    private String deliveryInstruction;
    @Enumerated
    private ShippingStatus shippingStatus;

    @OneToOne
    private Address address;
}
