package com.miu.onlinemarketplace.service.orderPay;

import com.miu.onlinemarketplace.common.dto.*;
import com.miu.onlinemarketplace.common.enums.CardBrand;
import com.miu.onlinemarketplace.common.enums.OrderPayStatus;
import com.miu.onlinemarketplace.common.enums.OrderStatus;
import com.miu.onlinemarketplace.entities.*;
import com.miu.onlinemarketplace.exception.ConflictException;
import com.miu.onlinemarketplace.repository.*;
import com.miu.onlinemarketplace.security.AppSecurityUtils;
import com.miu.onlinemarketplace.service.email.emailsender.EmailSenderService;
import com.miu.onlinemarketplace.service.thirdParty.ThirdPartyService;
import com.miu.onlinemarketplace.utils.Utility;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class OrderPayServiceImpl implements OrderPayService{
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CardInfoRepository cardInfoRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderPayRepository orderPayRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    private final ThirdPartyService thirdPartyService;
    private final EmailSenderService emailSenderService;


    private Long getLoggedInUserId(){
        return AppSecurityUtils.getCurrentUserId().isPresent() ? AppSecurityUtils.getCurrentUserId().get(): null;
    }

    @Override
    public OrderPayInfoDto findOrderPayInfo(List<ShoppingCartDto> shoppingCartDtos) {

        OrderPayInfoDto infoDto = new OrderPayInfoDto();
        if(getLoggedInUserId() != null)
            checkForLoggedInUser().accept(infoDto);
        checkOrderCalculation(shoppingCartDtos, infoDto);

        return infoDto;
    }

    private OrderPayInfoDto checkCalculation(List<ShoppingCartDto> shoppingCartDtos, OrderPayInfoDto infoDto) {
        double totalPrice = 0;
        int totalQuantity = 0;
        for (ShoppingCartDto dto: shoppingCartDtos) {
            totalPrice += dto.getProduct().getPrice() * dto.getQuantity();
            totalPrice += totalPrice * Utility.TAX/100;
            totalPrice = Double.valueOf(String.format("%.2f", totalPrice));
            totalQuantity += dto.getQuantity();
        }
        infoDto.setPrice(totalPrice);
        infoDto.setQuantity(totalQuantity);
        return infoDto;
    }

    private Consumer<OrderPayInfoDto> checkForLoggedInUser() {
        return (infoDto) -> {
            Optional<ShoppingCart> shoppingCartOpt = shoppingCartRepository.findByUserUserIdOrderByCreatedDateDesc(getLoggedInUserId());
            if(shoppingCartOpt.isPresent()) {
                ShoppingCart shoppingCart = shoppingCartOpt.get();
                infoDto.setUserId(getLoggedInUserId());
                infoDto.setFullName(shoppingCart.getUser().getFullName());

                List<CardInfo> cardInfos = cardInfoRepository.findByUserUserId(getLoggedInUserId());
                List<CardInfoDto> dtos = new ArrayList<>();
                if(cardInfos.size() > 0){
                    for (CardInfo cardInfo: cardInfos) {
                        CardInfoDto cardInfoDto = modelMapper.map(cardInfo, CardInfoDto.class);
                        String cardNumber = cardInfoDto.getCardNumber();
                        cardNumber = cardNumber.substring(cardNumber.length()-4);
                        cardInfoDto.setCardNumber("XXXX-XXXX-XXXX-".concat(cardNumber));
                        dtos.add(cardInfoDto);
                    }
                    infoDto.setCardInfoDtos(dtos);
                }

                List<Address> addresses = addressRepository.findByUserUserId(getLoggedInUserId());
                List<AddressDto> addressDtos = new ArrayList<>();
                if(addresses.size() > 0){
                    for (Address address: addresses ) {
                        addressDtos.add(modelMapper.map(address, AddressDto.class));
                    }
                    infoDto.setAddressDtos(addressDtos);
                }
            }
        };
    }

    @Override
    public OrderPayResponseDto createOrderPay(OrderPayDto orderPayDto) {

        try {
            boolean addressFlag = false, cardInfoFlag = false;
            thirdPartyService.validateStripe(orderPayDto);
            OrderPayInfoDto infoDto = checkCalculation(orderPayDto.getShoppingCartDtos(), new OrderPayInfoDto());
            validateUser(orderPayDto, infoDto);
            if(getLoggedInUserId() != null) {
                addressFlag = validateAddress(orderPayDto.getAddressDto());
                cardInfoFlag = validateCardInfo(orderPayDto.getCardInfoDto());
            }
            OrderPay orderPaydb = saveOrderPay(orderPayDto, addressFlag, cardInfoFlag);
            emailSenderService.sendPaymentNotification(orderPayDto);

            Order order = saveOrder(orderPaydb);
            saveOrderItem(orderPayDto, order);
//            shoppingCartRepository.re

            return new OrderPayResponseDto().builder()
                    .message("Payment success!")
                    .body(order.getOrderId())
                    .orderPayStatus(orderPaydb.getOrderPayStatus().toString())
                    .success(true)
                    .httpStatus(HttpStatus.OK)
                    .build();
        } catch (StripeException e) {
            throw new ConflictException("Invalid token!");
        }

    }

    private void saveOrderItem(OrderPayDto orderPayDto, Order order) {
        for (ShoppingCartDto dto: orderPayDto.getShoppingCartDtos()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(dto.getQuantity());
            orderItem.setDiscount(0D);
            Double totalPrice = dto.getProduct().getPrice() * dto.getQuantity();
            double tax = totalPrice * Utility.TAX/100;
            totalPrice += tax;
            totalPrice = Double.valueOf(String.format("%.2f", totalPrice));

            orderItem.setPrice(totalPrice);
            orderItem.setTax(tax);
            orderItem.setQuantity(dto.getQuantity());
            orderItem.setIsCommissioned(false);
            orderItem.setProduct(productRepository.findById(dto.getProduct().getProductId()).get());
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        }
    }

    @NotNull
    private Order saveOrder(OrderPay orderPaydb) {
        Order order = new Order();
        List<OrderPay> orderPayList = new ArrayList<>();
        orderPayList.add(orderPaydb);

        if(orderPaydb.getOrderPayStatus().equals(OrderPayStatus.COMPLETE))
            order.setOrderStatus(OrderStatus.CONFIRMED);
        else if(orderPaydb.getOrderPayStatus().equals(OrderPayStatus.PENDING))
            order.setOrderStatus(OrderStatus.PENDING);
        else if(orderPaydb.getOrderPayStatus().equals(OrderPayStatus.FAILURE))
            order.setOrderStatus(OrderStatus.CANCELED);
        else order.setOrderStatus(OrderStatus.SHIPPED);

        order.setOrderCode(UUID.randomUUID().toString());
        order.setUser(getLoggedInUserId() != null ? userRepository.findById(getLoggedInUserId()).get() : null);
        return orderRepository.save(order);
    }

    private OrderPayInfoDto checkOrderCalculation(List<ShoppingCartDto> shoppingCartDtos, OrderPayInfoDto infoDto) {
        double totalPrice = 0;
        int totalQuantity = 0;

        for (ShoppingCartDto dto: shoppingCartDtos) {
            totalPrice += dto.getProduct().getPrice() * dto.getQuantity();
            totalPrice += totalPrice * Utility.TAX/100;
            totalPrice = Double.valueOf(String.format("%.2f", totalPrice));
            totalQuantity += dto.getQuantity();
        }
        infoDto.setPrice(totalPrice);
        infoDto.setQuantity(totalQuantity);
        return infoDto;
    }

    private void validateUser(OrderPayDto orderPayDto, OrderPayInfoDto infoDto) {
        if(getLoggedInUserId() != null && orderPayDto.getIsGuestUser())
            throw new ConflictException("Not a guest user!");

        if(infoDto.getPrice() != orderPayDto.getPrice()){
            throw new ConflictException("Price on transaction do not match!");
        }
    }

    private boolean validateAddress(AddressDto addressDto) {
        boolean addressFlag = false;
        List<Address> addresses = addressRepository.findByUserUserId(getLoggedInUserId());
        if(addresses.size() > 0){
            for (Address address: addresses ) {
                if( (address.getAddress1().equals(addressDto.getAddress1()) || address.getAddress2().equals(addressDto.getAddress2())) &&
                    address.getCity().equals(addressDto.getCity()) &&
                    address.getState().equals(addressDto.getState()) &&
                    address.getZipCode().equals(addressDto.getZipCode()) &&
                    address.getCountry().equals(addressDto.getCountry())){
                    addressFlag = true;
                    break;
                }
            }
        }
        return addressFlag;
    }

    private boolean validateCardInfo(CardInfoDto cardInfoDto) {
        boolean cardInfoFlag = false;
        List<CardInfo> cardInfos = cardInfoRepository.findByUserUserId(getLoggedInUserId());
        for (CardInfo cardInfo: cardInfos ) {
            if(cardInfo.getLastFourDigits() == cardInfoDto.getLastFourDigits() &&
                cardInfo.getExpMonth() == cardInfoDto.getExpMonth() &&
                cardInfo.getExpYear() == cardInfoDto.getExpYear() &&
                cardInfo.getCvc().equals(cardInfoDto.getCvc()) &&
                cardInfo.getCardBrand().toString().toUpperCase().equals(cardInfoDto.getCardBrand().toUpperCase()))
            cardInfoFlag = true;
            break;
        }
        return cardInfoFlag;
    }


    private OrderPay saveOrderPay(OrderPayDto orderPayDto,boolean addressFlag, boolean cardInfoFlag) {
        OrderPay orderPay = new OrderPay();
        orderPay.setIsGuestUser(orderPayDto.getIsGuestUser());
        orderPay.setClientIp(orderPayDto.getClientIp());
        orderPay.setCardId(orderPayDto.getCardId());
        orderPay.setOrderPayStatus(OrderPayStatus.COMPLETE);
        orderPay.setTransactionId(orderPayDto.getTransactionId());

        orderPay.setUserId(orderPayDto.getUserId());
        orderPay.setFullName(orderPayDto.getFullName());
        orderPay.setEmail(orderPayDto.getEmail());
        orderPay.setPrice(orderPayDto.getPrice());

        User user = null;
        if(getLoggedInUserId() != null){
            user = userRepository.findById(getLoggedInUserId()).get();
        }

        Address address = null;
        CardInfo cardInfo = null;
        if(!addressFlag) {
            address = modelMapper.map(orderPayDto.getAddressDto(), Address.class);
            address.setUser(user);
            orderPay.setAddress(address);
        }
        if(!cardInfoFlag) {
            cardInfo = modelMapper.map(orderPayDto.getCardInfoDto(), CardInfo.class);
            cardInfo.setUser(user);
            if(orderPayDto.getCardInfoDto().getCardBrand().toUpperCase().equals(CardBrand.VISA.toString().toUpperCase()))
                cardInfo.setCardBrand(CardBrand.VISA);
            if(orderPayDto.getCardInfoDto().getCardBrand().toUpperCase().equals(CardBrand.MASTERCARD.toString().toUpperCase()))
                cardInfo.setCardBrand(CardBrand.MASTERCARD);
            else cardInfo.setCardBrand(CardBrand.AMEX);
            orderPay.setCardInfo(cardInfo);
        }


        return orderPayRepository.save(orderPay);
    }


}
