package com.miu.onlinemarketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCommission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountCommissionId;
    private Long orderId;
    private Double vendorCommission;
    private Double platformCommission;
    private Date createdDate;

    @OneToOne()
    @JoinColumn(name = "orderItemId", referencedColumnName = "orderItemId")
    private OrderItem orderItem;
}
