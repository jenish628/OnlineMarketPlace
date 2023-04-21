package com.miu.onlinemarketplace.service.invoice;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.miu.onlinemarketplace.common.dto.InvoiceDto;
import com.miu.onlinemarketplace.common.dto.OrderDto;
import com.miu.onlinemarketplace.common.dto.OrderItemDto;
import com.miu.onlinemarketplace.entities.Order;
import com.miu.onlinemarketplace.entities.OrderItem;
import com.miu.onlinemarketplace.repository.OrderRepository;
import com.miu.onlinemarketplace.repository.OrderItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private OrderRepository orderInvoiceRepository;
    private OrderItemRepository orderItemInvoiceRepository;

    private ModelMapper modelMapper;

    public InvoiceServiceImpl(OrderRepository orderInvoiceRepository, OrderItemRepository orderItemInvoiceRepository, ModelMapper modelMapper) {
        this.orderInvoiceRepository = orderInvoiceRepository;
        this.orderItemInvoiceRepository = orderItemInvoiceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public InvoiceDto generateInvoiceByOrderId(Long orderId) {
        List<OrderItem> orderItemList = orderItemInvoiceRepository.findAllOrderItemByOrderId(orderId);
        List<OrderItemDto> orderItemDtoList =  orderItemList.stream()
                .map(orderItem -> modelMapper.map(orderItem, OrderItemDto.class))
                .collect(Collectors.toList());
        double total = 0;
        for (OrderItemDto orderItem: orderItemDtoList) {
            total += orderItem.getQuantity() * orderItem.getPrice();
        }

        Order order = orderInvoiceRepository.findById(orderId).get();
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        InvoiceDto invoiceDto = new InvoiceDto();

        // TODO
//        orderItemList // entity list, convert to dto OrderItemDto list

//        List<com.miu.onlinemarketplace.common.dto.OrderItem> itemListDto = orderItemList.stream().map(orderItem -> {
//            Order orderId1 = orderItem.getOrderId();
//            com.miu.onlinemarketplace.common.dto.OrderItem oiDto = new com.miu.onlinemarketplace.common.dto.OrderItem();
//            BeanUtils.copyProperties(oiDto, orderItem);
//            return oiDto;
//        }).collect(Collectors.toList());
        invoiceDto.setOrderPlaced(orderDto.getOrderDate());
        invoiceDto.setOrderItemList(orderItemDtoList);
        invoiceDto.setPaymentMethod(orderDto.getPayments());
        invoiceDto.setShippingAddress(orderDto.getShipping());
        invoiceDto.setOrderNumber(order.getOrderId());
        invoiceDto.setTotal(total);
        return invoiceDto;
    }
}
