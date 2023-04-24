package com.miu.onlinemarketplace.service.payment;

import com.miu.onlinemarketplace.utils.Utility;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripePaymentServiceImpl implements StripePaymentService {
    public StripePaymentServiceImpl() {
        Stripe.apiKey = Utility.SECRET_KEY;
    }

    @Override
    public Charge pay(String code, Double totalAmount) {
        try{
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", (int)(totalAmount * 100));
            chargeParams.put("currency", "USD");
            chargeParams.put("source", code);
            Charge charge = Charge.create(chargeParams);
            return charge;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new Charge();
        }
    }
}
