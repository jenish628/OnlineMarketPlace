package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "Select oi from OrderItem oi where oi.order.OrderId = ?1")
    List<OrderItem> findAllOrderItemByOrderId(Long orderId);

    List<OrderItem> findAllByIsCommissionedFalse();

}
