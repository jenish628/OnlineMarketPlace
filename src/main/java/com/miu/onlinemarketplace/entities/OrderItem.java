package com.miu.onlinemarketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderItemId;
    private Double price;
    private Double tax;
    private Integer quantity;
    private Double discount;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product product;


    @ManyToOne()
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order orderId;

}
