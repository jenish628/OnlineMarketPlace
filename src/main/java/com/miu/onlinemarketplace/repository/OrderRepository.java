package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderCode(String orderCode);

    Page<Order> findAllByUser_UserId(Long userId, Pageable pageable);

    //order list, items [] // only vendor vendors

    @Query("SELECT COUNT(DISTINCT oi.order) FROM OrderItem oi "
            + "JOIN oi.order o "
            + "JOIN oi.product p "
            + "WHERE p.vendor.vendorId = :vendorId")
    Long countOrdersByVendorId(@Param("vendorId") Long vendorId);

    @Query("SELECT DISTINCT oi.order FROM OrderItem oi "
            + "JOIN oi.order o "
            + "JOIN oi.product p "
            + "WHERE p.vendor.vendorId = :vendorId")
    Page<Order> findOrdersByVendorId(@Param("vendorId") Long vendorId, Pageable pageable);

}
