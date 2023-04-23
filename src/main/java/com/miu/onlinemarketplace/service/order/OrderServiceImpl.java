package com.miu.onlinemarketplace.service.order;

import com.miu.onlinemarketplace.common.dto.CheckingOrderDto;
import com.miu.onlinemarketplace.common.dto.OrderDto;
import com.miu.onlinemarketplace.common.dto.OrderItemDto;
import com.miu.onlinemarketplace.common.enums.OrderItemStatus;
import com.miu.onlinemarketplace.common.enums.OrderStatus;
import com.miu.onlinemarketplace.entities.Order;
import com.miu.onlinemarketplace.entities.OrderItem;
import com.miu.onlinemarketplace.exception.AppSecurityException;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.OrderItemRepository;
import com.miu.onlinemarketplace.repository.OrderRepository;
import com.miu.onlinemarketplace.security.AppSecurityUtils;
import com.miu.onlinemarketplace.security.models.EnumRole;
import com.miu.onlinemarketplace.utils.GenerateRandom;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public OrderDto saveOrder(OrderDto orderDto) {
        orderDto.setOrderCode(GenerateRandom.random());
        Order order = modelMapper.map(orderDto, Order.class);
        return modelMapper.map(orderRepository.save(order), OrderDto.class);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException());
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public Boolean deleteOrderById(Long orderId) {
        return null;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return null;
    }

    @Override
    public List<OrderDto> getAllOrderByUserId(Long userId) {
        return null;
    }

    @Override
    public CheckingOrderDto getAllOrderItemsByOrderCode(String orderIdAndCode) {
        try {
            String st[] = orderIdAndCode.split("-");
            Long orderId = Long.valueOf(st[0]);
            String orderCode = st[1];
            Order order = orderRepository.findByIdAndOrderCode(orderId, orderCode);
            List<OrderItem> orderItems = orderItemRepository.findAllOrderItemByOrderId(order.getOrderId());
            List<OrderItemDto> orderItemDtoList = orderItems.stream()
                    .map(orderItem -> modelMapper.map(orderItem, OrderItemDto.class))
                    .collect(Collectors.toList());
            double total = 0.0;
            for (OrderItemDto o: orderItemDtoList) {
                total += (o.getQuantity() * o.getPrice()) - o.getDiscount() - o.getTax();
            }
            CheckingOrderDto checkingOrderDto = new CheckingOrderDto();
            checkingOrderDto.setOrderId(order.getOrderId());
            checkingOrderDto.setOrderDate(order.getOrderDate());
            checkingOrderDto.setOrderStatus(order.getOrderStatus());
            checkingOrderDto.setTotal(total);
            checkingOrderDto.setOrderCode(order.getOrderCode());
            checkingOrderDto.setOrderItemDto(orderItemDtoList);
            checkingOrderDto.setShippingStatus(order.getShipping().getShippingStatus());
            return checkingOrderDto;
        } catch (NullPointerException ex) {
            throw new RuntimeException();
        }
    }

//    @Override
//    public OrderDto patchOrder(OrderDto orderDto) {
//        Order order = modelMapper.map(orderDto, Order.class);
//        return modelMapper.map(orderRepository.save(order), OrderDto.class);
//    }

    @Override
    public boolean updateOrderStatus(Long orderId) {
        EnumRole enumRole = AppSecurityUtils.getCurrentUserRole().orElseThrow(() -> new AppSecurityException("Must be loggedId"));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order Not Found"));
        if(enumRole == EnumRole.ROLE_ADMIN){
            // if admin, OrderStatus is set to DELIVERED
            order.setOrderStatus(OrderStatus.DELIVERED);
            orderRepository.save(order);
        } else if (enumRole == EnumRole.ROLE_VENDOR) {
            // if vendor, find all orderItems of orderId, and set to SHIPPED
            List<OrderItem> allOrderItemByOrderId = orderItemRepository.findAllOrderItemByOrderId(order.getOrderId());
            for(OrderItem orderItem : allOrderItemByOrderId){
                orderItem.setOrderItemStatus(OrderItemStatus.WAREHOUSE_SHIP);
            }
            orderItemRepository.saveAll(allOrderItemByOrderId);
        } else {
            // if user, update status to RECEIVED
            order.setOrderStatus(OrderStatus.RECEIVED);
            orderRepository.save(order);
        }
        return true;
    }

}
