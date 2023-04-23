package com.miu.onlinemarketplace.service.thirdParty;

import com.miu.onlinemarketplace.common.dto.OrderPayDto;
import com.miu.onlinemarketplace.utils.Utility;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Token;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyServiceImpl implements ThirdPartyService{
    @Override
    public void validateStripe(OrderPayDto orderPayDto) {
        Stripe.apiKey = Utility.SECRET_KEY;
        String stripeTokenId = orderPayDto.getTransactionId();

        try {
            Token stripeToken = Token.retrieve(stripeTokenId);
            System.out.println("-----validateStripe------- "+stripeToken);
            System.out.println("Stripe token is valid");
            // Do whatever you want to do if the token is valid...
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (ApiConnectionException e) {
            e.printStackTrace();
        } catch (CardException e) {
            System.out.println("Stripe token is invalid: " + e.getMessage());
            // Do whatever you want to do if the token is invalid...
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (StripeException e){
            e.printStackTrace();
        }

    }
}
