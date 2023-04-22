package com.miu.onlinemarketplace.service.order;

import com.miu.onlinemarketplace.common.dto.CheckingOrderDto;
import com.miu.onlinemarketplace.common.dto.OrderDto;
import com.miu.onlinemarketplace.common.dto.OrderItemDto;
import com.miu.onlinemarketplace.entities.Order;
import com.miu.onlinemarketplace.entities.OrderItem;
import com.miu.onlinemarketplace.repository.OrderItemRepository;
import com.miu.onlinemarketplace.repository.OrderRepository;
import com.miu.onlinemarketplace.utils.GenerateRandom;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public CheckingOrderDto getAllOrderItemsByOrderCode(String orderCode) {
        try {
            Order order = orderRepository.findByOrderCode(orderCode);
            List<OrderItem> orderItems = orderItemRepository.findAllOrderItemByOrderId(order.getOrderId());
            List<OrderItemDto> orderItemDtoList = orderItems.stream()
                    .map(orderItem -> modelMapper.map(orderItem, OrderItemDto.class))
                    .collect(Collectors.toList());
            CheckingOrderDto checkingOrderDto = new CheckingOrderDto();
            checkingOrderDto.setOrderId(order.getOrderId());
            checkingOrderDto.setOrderDate(order.getOrderDate());
            checkingOrderDto.setOrderStatus(order.getOrderStatus());
            checkingOrderDto.setOrderCode(order.getOrderCode());
            checkingOrderDto.setOrderItemDto(orderItemDtoList);
            checkingOrderDto.setShippingStatus(order.getShipping().getShippingStatus());
            return checkingOrderDto;
        } catch (NullPointerException ex) {
            throw new RuntimeException();
        }
    }
}