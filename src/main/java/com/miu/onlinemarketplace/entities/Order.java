package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long OrderId;
    private String name;
    @Enumerated
    private OrderStatus orderStatus;
}
