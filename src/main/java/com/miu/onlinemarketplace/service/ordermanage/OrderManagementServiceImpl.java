package com.miu.onlinemarketplace.service.ordermanage;

import com.miu.onlinemarketplace.common.dto.OrderDto;
import com.miu.onlinemarketplace.common.dto.OrderItemDto;
import com.miu.onlinemarketplace.common.dto.OrderResponseDto;
import com.miu.onlinemarketplace.entities.Order;
import com.miu.onlinemarketplace.entities.OrderItem;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.OrderItemRepository;
import com.miu.onlinemarketplace.repository.OrderRepository;
import com.miu.onlinemarketplace.security.AppSecurityUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderManagementServiceImpl implements OrderManagementService {


    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    public OrderManagementServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
    }

    public Page<OrderResponseDto> getAllOrderOfCurrentUser(Pageable pageable) {
        Long userId = AppSecurityUtils.getCurrentUserId().orElseThrow(() -> new DataNotFoundException("User ID Not Found")); //get current userId
        Page<Order> orderPage = orderRepository.findAllByUser_UserId(userId, pageable);
        List<OrderResponseDto> orderResponseDtoList = orderPage.getContent().stream()
                .map(order -> {
                    List<OrderItem> orderItemList = orderItemRepository.findAllOrderItemByOrderId(order.getOrderId());
                    return mapToOrderResponseDto(order, orderItemList);
                }).toList();
        return new PageImpl<>(orderResponseDtoList, pageable, orderPage.getTotalElements());
    }

    public Page<OrderResponseDto> getAllOrderByVendor(Pageable pageable) {
        //EnumRole enumRole = AppSecurityUtils.getCurrentUserRole().orElseThrow(() -> new AppSecurityException("Required Role, but not available"));
        Long vendorId = AppSecurityUtils.getCurrentUserId().orElseThrow(() -> new DataNotFoundException("User ID Not Found"));// if currentUser role is vendor, get id

        Page<Order> orderPage = orderRepository.findOrdersByVendorId(vendorId, pageable);
//        Long totalOrderOfVendor = orderRepository.countOrdersByVendorId(vendorId);
        List<OrderResponseDto> orderResponseDtoList = orderPage.getContent().stream()
                .map(order -> {
                    List<OrderItem> orderItemList = orderItemRepository.findAllOrderItemByOrderIdAndVendorId(order.getOrderId(), vendorId);
                    return mapToOrderResponseDto(order, orderItemList);
                }).toList();
        return new PageImpl<>(orderResponseDtoList, pageable, orderPage.getTotalElements());
    }

    public Page<OrderResponseDto> getAllOrders(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);
        List<OrderResponseDto> orderResponseDtoList = orderPage.stream().map(order -> {
            List<OrderItem> orderItemList = orderItemRepository.findAllOrderItemByOrderId(order.getOrderId());
            return mapToOrderResponseDto(order, orderItemList);
        }).toList();
        return new PageImpl<>(orderResponseDtoList, pageable, orderPage.getTotalElements());
    }

    public OrderResponseDto mapToOrderResponseDto(Order order, List<OrderItem> orderItemList) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        List<OrderItemDto> orderItemDtoList = orderItemList.stream()
                .map(orderItem -> modelMapper.map(orderItem, OrderItemDto.class))
                .toList();
        // returning OrderResponseDto
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderDto(orderDto);
        orderResponseDto.setRelatedOrderItems(orderItemDtoList);
        return orderResponseDto;
    }

}
