package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.OrderPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPayRepository extends JpaRepository<OrderPay, Long> {
}
