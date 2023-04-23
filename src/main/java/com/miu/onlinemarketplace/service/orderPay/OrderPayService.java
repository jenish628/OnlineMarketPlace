package com.miu.onlinemarketplace.service.orderPay;

import com.miu.onlinemarketplace.common.dto.OrderPayDto;
import com.miu.onlinemarketplace.common.dto.OrderPayResponseDto;
import com.miu.onlinemarketplace.common.dto.ShoppingCartDto;
import com.miu.onlinemarketplace.common.dto.OrderPayInfoDto;

import java.util.List;

public interface OrderPayService {

    OrderPayInfoDto findOrderPayInfo(List<ShoppingCartDto> shoppingCartDtos);

    OrderPayResponseDto createOrderPay(OrderPayDto orderPayDto);
}
