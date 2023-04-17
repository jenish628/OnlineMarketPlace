package com.miu.onlinemarketplace.entities;


import com.miu.onlinemarketplace.common.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long OrderId;
    @Enumerated
    private OrderStatus orderStatus;
    private Boolean isCommissioned;
//    @Enumerated
//    private AddressType addressType;

    @OneToOne
    private Shipping shipping;

    @ManyToOne
    private User userId;

    private LocalDateTime orderDate;


//    @ManyToMany
//    List<Payment> payments;


}
