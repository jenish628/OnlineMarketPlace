package com.miu.onlinemarketplace.service.impl;

import com.miu.onlinemarketplace.common.dto.OrderPayDto;
import com.miu.onlinemarketplace.common.dto.ResponseDto;
import com.miu.onlinemarketplace.common.enums.CardBrand;
import com.miu.onlinemarketplace.entities.Address;
import com.miu.onlinemarketplace.entities.CardInfo;
import com.miu.onlinemarketplace.entities.OrderPay;
import com.miu.onlinemarketplace.entities.User;
import com.miu.onlinemarketplace.repository.CardInfoRepository;
import com.miu.onlinemarketplace.repository.OrderPayRepository;
import com.miu.onlinemarketplace.repository.UserRepository;
import com.miu.onlinemarketplace.service.payment.OrderPayService;
import com.miu.onlinemarketplace.utils.Utility;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class OrderPayServiceImpl implements OrderPayService {

    private final OrderPayRepository orderPayRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public ResponseDto createOrderPay(OrderPayDto orderPayDto) {

        OrderPay orderPay = new OrderPay();

        Optional<User> userOpt = userRepository.findById(orderPayDto.getUserId());
        setBasicInfo(orderPayDto).accept(orderPay);
        setShippingAddress(orderPayDto).accept(orderPay);
        setCardInfo(orderPayDto, userOpt.get()).accept(orderPay);

        OrderPay dbOrderPay = orderPayRepository.save(orderPay);
        if(dbOrderPay.getId() != null){
            return ResponseDto.builder().isSuccess(true)
                    .message("Saved successfully!")
                    .status(HttpStatus.CREATED)
                    .build();
        }
        return ResponseDto.builder()
                .isSuccess(false)
                .message("Saved failure")
                .status(HttpStatus.CONFLICT)
                .build();
    }

    private Consumer<OrderPay> setBasicInfo(OrderPayDto orderPayDto) {
        return (orderPay -> {
            orderPay.setUserId(orderPayDto.getUserId());
            orderPay.setFullName(orderPayDto.getFullName());
            orderPay.setQuantity(orderPayDto.getQuantity());

            Double taxAdded = Utility.ORDER_TAX * orderPayDto.getPrice();
            Double totalPrice = taxAdded + orderPayDto.getPrice();
            orderPay.setTotalPrice(totalPrice);
        });
    }

    private Consumer<OrderPay> setShippingAddress(OrderPayDto orderPayDto) {
        return(orderPay -> {
            Address shippingAddress = new Address();
            shippingAddress.setAddress1(orderPayDto.getAddress());
            shippingAddress.setCity(orderPayDto.getCity());
            shippingAddress.setState(orderPayDto.getState());
            shippingAddress.setZipCode(orderPayDto.getZipcode());
            shippingAddress.setCountry(orderPayDto.getCountry());
            orderPay.setShippingAddress(shippingAddress);
        });
    }

    private Consumer<OrderPay> setCardInfo(OrderPayDto orderPayDto, User user) {
        return(orderPay -> {
            CardInfo cardInfo = new CardInfo();
            cardInfo.setUser(user);
            cardInfo.setCardNumber(orderPayDto.getCardNumber());
            cardInfo.setSecurityCode(orderPayDto.getSecurityCode());
            cardInfo.setExpiryMonth(orderPayDto.getExpiryMonth());
            cardInfo.setExpiryYear(orderPayDto.getExpiryYear());

            CardBrand cardBrand=null;
            if(orderPayDto.getCardBrand().equals("Master Card")) cardBrand = CardBrand.MASTERCARD;
            else if(orderPayDto.getCardBrand().equals("Visa")) cardBrand = CardBrand.VISA;
            else if(orderPayDto.getCardBrand().equals("Stripe")) cardBrand = CardBrand.STRIPE;
            else cardBrand=CardBrand.AMEX;
            cardInfo.setCardBrand(cardBrand);
            orderPay.setCardInfo(cardInfo);
        });
    }



}
