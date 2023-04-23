package com.miu.onlinemarketplace.service.payment;

import com.miu.onlinemarketplace.service.payment.dtos.TransactionResponseDto;

public interface StripePaymentService extends PaymentProvider {
    TransactionResponseDto pay(String code, Double totalAmount);
}
