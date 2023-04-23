package com.miu.onlinemarketplace.service.payment;

import com.miu.onlinemarketplace.service.payment.dtos.TransactionResponseDto;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StripePaymentServiceImpl implements StripePaymentService {
    @Override
    public TransactionResponseDto pay(String code, Double totalAmount) {
        Charge charge = new Charge();
        charge.setPaid(true);
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setTransactionId(UUID.randomUUID().toString());
        transactionResponseDto.setAmount(totalAmount);
        transactionResponseDto.setPaid(true);
        return transactionResponseDto;
    }
}
