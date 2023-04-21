package com.miu.onlinemarketplace.service.user;

import com.miu.onlinemarketplace.common.dto.AddressDto;
import com.miu.onlinemarketplace.common.dto.CardInfoDto;
import com.miu.onlinemarketplace.common.dto.UserCardInfoDto;
import com.miu.onlinemarketplace.entities.Address;
import com.miu.onlinemarketplace.entities.CardInfo;
import com.miu.onlinemarketplace.entities.ShoppingCart;
import com.miu.onlinemarketplace.entities.User;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.AddressRepository;
import com.miu.onlinemarketplace.repository.CardInfoRepository;
import com.miu.onlinemarketplace.repository.ShoppingCartRepository;
import com.miu.onlinemarketplace.repository.UserRepository;
import com.miu.onlinemarketplace.security.AppSecurityUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CardInfoRepository cardInfoRepository;

    private final ShoppingCartRepository shoppingCartRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserCardInfoDto findInfoById() {

        UserCardInfoDto dto = new UserCardInfoDto();
        Long userId = AppSecurityUtils.getCurrentUserId().orElseThrow(()-> new DataNotFoundException("User ID Not Found"));
        Optional<ShoppingCart> shoppingCartOpt = shoppingCartRepository.findByUserUserId(userId);
        if(shoppingCartOpt.isPresent()) {
           ShoppingCart shoppingCart = shoppingCartOpt.get();
           dto.setUserId(userId);
           dto.setFullName(shoppingCart.getUser().getFullName());
           dto.setQuantity(shoppingCart.getQuantity());
//           dto.setPrice(shoppingCart.getProduct().getP);
           Optional<CardInfo> cardInfoOpt = cardInfoRepository.findByUserUserId(userId);
           System.out.println("----------address :::: "+cardInfoOpt.toString());
           cardInfoOpt.ifPresent(cardInfo -> dto.setCardInfoDto(modelMapper.map(cardInfo, CardInfoDto.class)));

           Optional<Address> addressOpt = addressRepository.findByUser(shoppingCart.getUser());
           addressOpt.ifPresent(address -> dto.setAddressDto(modelMapper.map(address, AddressDto.class)));
           System.out.println(addressOpt.toString());
        }
        return dto;
    }
}
