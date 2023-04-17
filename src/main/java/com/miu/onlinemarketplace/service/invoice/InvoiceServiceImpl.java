package com.miu.onlinemarketplace.service.invoice;

import com.miu.onlinemarketplace.common.dto.InvoiceDto;
import com.miu.onlinemarketplace.entities.Order;
import com.miu.onlinemarketplace.entities.OrderItem;
import com.miu.onlinemarketplace.repository.invoice.OrderInvoiceRepository;
import com.miu.onlinemarketplace.repository.invoice.OrderItemInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private OrderInvoiceRepository orderInvoiceRepository;
    private OrderItemInvoiceRepository orderItemInvoiceRepository;

    public InvoiceServiceImpl(@Autowired OrderInvoiceRepository orderInvoiceRepository,@Autowired OrderItemInvoiceRepository orderItemInvoiceRepository) {
        this.orderInvoiceRepository = orderInvoiceRepository;
        this.orderItemInvoiceRepository = orderItemInvoiceRepository;
    }

    @Override
    public InvoiceDto generateInvoiceByOrderId(Long orderId) {
        List<OrderItem> orderItemList = orderItemInvoiceRepository.findAllOrderItemByOrderId(orderId);
        double total = 0;
        for (OrderItem orderItem: orderItemList) {
            total += orderItem.getQuantity() * orderItem.getPrice();
        }
        Order order = orderInvoiceRepository.findById(orderId).get();
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setOrderItemList(orderItemList);
        invoiceDto.setPaymentMethod(order.getPayments());
        invoiceDto.setShippingAddress(order.getShipping().getAddress());
        invoiceDto.setOrderNumber(order.getOrderId());
        invoiceDto.setTotal(total);
        return invoiceDto;
    }
}
