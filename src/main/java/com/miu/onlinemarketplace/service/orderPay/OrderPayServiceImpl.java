package com.miu.onlinemarketplace.service.orderPay;

import com.miu.onlinemarketplace.common.dto.*;
import com.miu.onlinemarketplace.common.enums.OrderPayStatus;
import com.miu.onlinemarketplace.entities.Address;
import com.miu.onlinemarketplace.entities.CardInfo;
import com.miu.onlinemarketplace.entities.OrderPay;
import com.miu.onlinemarketplace.entities.ShoppingCart;
import com.miu.onlinemarketplace.exception.ConflictException;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.*;
import com.miu.onlinemarketplace.security.AppSecurityUtils;
import com.miu.onlinemarketplace.utils.Utility;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class OrderPayServiceImpl implements OrderPayService{

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CardInfoRepository cardInfoRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderPayRepository orderPayRepository;
    private final ModelMapper modelMapper;

    private Long getLoggedInUserId(){
        return AppSecurityUtils.getCurrentUserId()
                .orElseThrow(()-> new DataNotFoundException("User ID Not Found"));

    }

    @Override
    public OrderPayInfoDto findOrderPayInfo(List<ShoppingCartDto> shoppingCartDtos) {

        OrderPayInfoDto infoDto = new OrderPayInfoDto();
        checkCalculation(shoppingCartDtos, infoDto);
        if(getLoggedInUserId() > 0)
            checkForLoggedInUser().accept(infoDto);

        System.out.println("=================== "+getLoggedInUserId());
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
            Long userId = getLoggedInUserId();
            Optional<ShoppingCart> shoppingCartOpt = shoppingCartRepository.findByUserUserId(userId);
            if(shoppingCartOpt.isPresent()) {
                ShoppingCart shoppingCart = shoppingCartOpt.get();
                infoDto.setUserId(userId);
                infoDto.setFullName(shoppingCart.getUser().getFullName());

                List<CardInfo> cardInfos = cardInfoRepository.findByUserUserId(userId);
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

                List<Address> addresses = addressRepository.findByUserUserId(userId);
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

        // address ...
        OrderPayInfoDto infoDto = checkCalculation(orderPayDto.getShoppingCartDtos(), new OrderPayInfoDto());
        validateUser(orderPayDto, infoDto);
        validateRegisteredUser(orderPayDto, infoDto);

        OrderPay orderPay = modelMapper.map(orderPayDto, OrderPay.class);
        orderPay.setOrderPayStatus(OrderPayStatus.PENDING);
        OrderPay orderPaydb = orderPayRepository.save(orderPay);

        OrderPayResponseDto responseDto = new OrderPayResponseDto().builder()
                .message("Payment on process")
                .orderPayStatus(orderPaydb.getOrderPayStatus().toString())
                .success(true)
                .httpStatus(HttpStatus.OK)
                .build();

        return responseDto;
    }

    private void validateUser(OrderPayDto orderPayDto, OrderPayInfoDto infoDto) {
        if(getLoggedInUserId() != null && orderPayDto.isGuestUser())
            throw new ConflictException("Not a guest user!");

        if(infoDto.getPrice() != orderPayDto.getPrice() ||
                infoDto.getQuantity() != orderPayDto.getQuantity() ){
            throw new ConflictException("Price and quantity do not match!");
        }
    }
    private void validateRegisteredUser(OrderPayDto orderPayDto, OrderPayInfoDto infoDto) {

//        if(infoDto.getCardInfoDto().getCardNumber() != orderPayDto.getCardNumber() ||
//                infoDto.getCardInfoDto().getNameOnCard() != orderPayDto.getNameOnCard() ||
//                infoDto.getCardInfoDto().getCvc() != orderPayDto.getCVC()){
//            throw new ConflictException("Payment card Information does not match.");
//        }

//        if(infoDto.getCardInfoDto().getExpMonth() != orderPayDto.getExpMonth() ||
//                infoDto.getCardInfoDto().getExpYear() != orderPayDto.getExpYear() )   {
//            throw new ConflictException("Validity date of card does not match.");
//        }
    }

}
