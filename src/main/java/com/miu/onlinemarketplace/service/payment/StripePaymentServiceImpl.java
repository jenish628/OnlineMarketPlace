package com.miu.onlinemarketplace.service.payment;

import com.stripe.model.Charge;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentServiceImpl implements StripePaymentService {
    @Override
    public Charge payViaStripe(String code, Double totalAmount) {
        Charge charge = new Charge();
        charge.setPaid(true);
        return charge;
    }
}
