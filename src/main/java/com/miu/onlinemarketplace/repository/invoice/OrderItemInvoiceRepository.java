package com.miu.onlinemarketplace.repository.invoice;

import com.miu.onlinemarketplace.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemInvoiceRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "Select * from order_item where order_id = ?1", nativeQuery = true)
    List<OrderItem> findAllOrderItemByOrderId(Long orderId);

}
