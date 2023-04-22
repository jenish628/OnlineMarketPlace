package com.miu.onlinemarketplace.service.payment;

import com.stripe.model.Charge;

public interface StripePaymentService {
    Charge payViaStripe(String code, Double totalAmount);
}
