package com.miu.onlinemarketplace.service.payment;

import com.miu.onlinemarketplace.common.dto.*;
import com.miu.onlinemarketplace.common.enums.*;
import com.miu.onlinemarketplace.entities.*;
import com.miu.onlinemarketplace.exception.ConflictException;
import com.miu.onlinemarketplace.repository.*;
import com.miu.onlinemarketplace.security.AppSecurityUtils;
import com.miu.onlinemarketplace.service.domain.shopping.ShoppingCartService;
import com.miu.onlinemarketplace.service.email.emailsender.EmailSenderService;
import com.miu.onlinemarketplace.service.thirdParty.ThirdPartyService;
import com.miu.onlinemarketplace.utils.Utility;
import com.stripe.model.Charge;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CardInfoRepository cardInfoRepository;
    private final ShippingRepository shippingRepository;

    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final PaymentRepository paymentRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    private final ThirdPartyService thirdPartyService;
    private final StripePaymentService stripePaymentService;
    private final EmailSenderService emailSenderService;


    private Long getLoggedInUserId(){
        return AppSecurityUtils.getCurrentUserId().isPresent() ? AppSecurityUtils.getCurrentUserId().get(): null;
    }

    @Override
    public OrderPayInfoDto findOrderPayInfo(List<ShoppingCartDto> shoppingCartDtos) {
        return checkCalculation(shoppingCartDtos);
    }

    private OrderPayInfoDto checkCalculation(List<ShoppingCartDto> shoppingCartDtos) {
        double totalPrice = 0, itemPrice=0;
        int totalQuantity = 0;
        OrderPayInfoDto infoDto = new OrderPayInfoDto();
        for (ShoppingCartDto dto: shoppingCartDtos) {
            itemPrice += dto.getProduct().getPrice() * dto.getQuantity();
            totalQuantity += dto.getQuantity();
        }
        infoDto.setItemPrice(itemPrice);
        totalPrice = itemPrice;
        totalPrice += itemPrice*(Utility.TAX)/100;
        int shippingCharge = (int)(itemPrice/50) * Utility.SHIPPING_CHARGE;
        infoDto.setPrice(Double.valueOf(String.format("%.2f", totalPrice+shippingCharge)));
        infoDto.setQuantity(totalQuantity);
        return infoDto;
    }

    private Consumer<OrderPayInfoDto> checkForLoggedInUser() {
        return (infoDto) -> {
            List<Address> addresses = addressRepository.findByUserUserId(getLoggedInUserId());
            List<AddressDto> addressDtos = new ArrayList<>();
            if(addresses.size() > 0){
                for (Address address: addresses ) {
                    addressDtos.add(modelMapper.map(address, AddressDto.class));
                }
                infoDto.setAddressDtos(addressDtos);
            }

        };
    }

    @Override
    public OrderPayResponseDto createOrderPay(OrderPayDto orderPayDto) {

        boolean addressFlag = false, cardInfoFlag = false;
//            thirdPartyService.validateStripe(orderPayDto);
        OrderPayInfoDto infoDto = checkCalculation(orderPayDto.getShoppingCartDtos());
        validateUser(orderPayDto, infoDto);
        if(getLoggedInUserId() != null) {
            addressFlag = validateAddress(orderPayDto.getAddressDto());
            cardInfoFlag = validateCardInfo(orderPayDto.getCardInfoDto());
        }

        Payment payment = setPayment(orderPayDto, addressFlag, cardInfoFlag);
        Charge charge = stripePaymentService.pay(orderPayDto.getTransactionId(), infoDto.getPrice());
        if(charge.getPaymentMethod() != null){
            if(!charge.getPaymentMethod().equals(orderPayDto.getCardId())) {
                payment.setOrderPayStatus(OrderPayStatus.FAILURE);
            } else {
                payment.setOrderPayStatus(OrderPayStatus.COMPLETE);
            }

        }else {
            payment.setOrderPayStatus(OrderPayStatus.PENDING);
        }
        payment = paymentRepository.save(payment);
        emailSenderService.sendPaymentNotification(orderPayDto);

        Order order = saveOrder(payment, charge);
        saveOrderItem(orderPayDto, order);

        return new OrderPayResponseDto().builder()
                .message("Payment success!")
                .body(order.getOrderId())
                .orderPayStatus(payment.getOrderPayStatus().toString())
                .success(true)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    private void saveOrderItem(OrderPayDto orderPayDto, Order order) {
        Shipping shipping = new Shipping();
        shipping.setShippingStatus(ShippingStatus.PENDING);
        shipping.setAddress(modelMapper.map(orderPayDto.getAddressDto(), Address.class));
        shipping.setDeliveryInstruction("");
        shippingRepository.save(shipping);
        order.setShipping(shipping);
        Order order1 = orderRepository.save(order);
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
            orderItem.setOrderItemStatus(OrderItemStatus.REQUESTED);
            orderItem.setProduct(productRepository.findById(dto.getProduct().getProductId()).get());
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
            shoppingCartService.removeProduct();
        }
    }

    @NotNull
    private Order saveOrder(Payment paymentDb, Charge charge) {
        Order order = new Order();

        if(charge.getPaymentMethod() != null){
            if(!charge.getPaymentMethod().equals(paymentDb.getCardId())) {
                order.setOrderStatus(OrderStatus.PENDING);
            } else {
                order.setOrderStatus(OrderStatus.CONFIRMED);
            }
        }else {
            order.setOrderStatus(OrderStatus.CANCELED);
        }
        order.setOrderCode(UUID.randomUUID().toString());
        order.setUser(getLoggedInUserId() != null ? userRepository.findById(getLoggedInUserId()).get() : null);

        List<Payment> payments = new ArrayList<>();
        payments.add(paymentDb);
        order.setPayments(payments);

        return orderRepository.save(order);
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

//               || address.getAddress2() != null ? address.getAddress2().equals(addressDto.getAddress2()) :
                if( (address.getAddress1().equals(addressDto.getAddress1()) ) &&
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
        if(cardInfos.size() > 0) {
            for (CardInfo cardInfo : cardInfos) {
                if (cardInfo.getLast4() == cardInfoDto.getLast4() &&
                        cardInfo.getExpMonth() == cardInfoDto.getExpMonth() &&
                        cardInfo.getExpYear() == cardInfoDto.getExpYear() &&
                        cardInfo.getCvc().equals(cardInfoDto.getCvc()) &&
                        cardInfo.getCardBrand().toString().toUpperCase().equals(cardInfoDto.getCardBrand().toUpperCase()))
                    cardInfoFlag = true;
                break;
            }
        }
        return cardInfoFlag;
    }


    private Payment setPayment(OrderPayDto orderPayDto,boolean addressFlag, boolean cardInfoFlag) {
        Payment orderPay = new Payment();
        orderPay.setIsGuestUser(orderPayDto.getIsGuestUser());
        orderPay.setClientIp(orderPayDto.getClientIp());
        orderPay.setCardId(orderPayDto.getCardId());
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

        return orderPay;
    }


}
