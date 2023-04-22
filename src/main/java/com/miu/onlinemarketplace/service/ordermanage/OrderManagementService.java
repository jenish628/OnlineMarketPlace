package com.miu.onlinemarketplace.service.ordermanage;

import com.miu.onlinemarketplace.common.dto.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderManagementService {
    Page<OrderResponseDto> getAllOrderOfCurrentUser(Pageable pageable);

    Page<OrderResponseDto> getAllOrderByVendor(Pageable pageable);

    Page<OrderResponseDto> getAllOrders(Pageable pageable);


}
