package com.miu.onlinemarketplace.service.payment;

import com.miu.onlinemarketplace.common.dto.OrderPayDto;
import com.miu.onlinemarketplace.common.dto.OrderPaymentDto;
import com.miu.onlinemarketplace.common.dto.ResponseDto;

public interface OrderPayService {

    ResponseDto createOrderPay(OrderPayDto orderPayDto);
}
