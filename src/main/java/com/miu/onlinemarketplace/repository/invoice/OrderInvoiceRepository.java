package com.miu.onlinemarketplace.repository.invoice;

import com.miu.onlinemarketplace.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInvoiceRepository extends JpaRepository<Order, Long> {

}
