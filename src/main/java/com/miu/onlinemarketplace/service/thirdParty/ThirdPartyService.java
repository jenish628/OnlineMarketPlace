package com.miu.onlinemarketplace.service.thirdParty;

import com.miu.onlinemarketplace.common.dto.OrderPayDto;
import com.stripe.exception.StripeException;

public interface ThirdPartyService {

    void validateStripe(OrderPayDto orderPayDto) throws StripeException;
}
